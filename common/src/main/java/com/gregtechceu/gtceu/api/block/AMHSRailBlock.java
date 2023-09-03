package com.gregtechceu.gtceu.api.block;

import com.gregtechceu.gtceu.api.capability.ICoverable;
import com.gregtechceu.gtceu.api.capability.IToolable;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.item.tool.IToolGridHighLight;
import com.gregtechceu.gtceu.api.pipenet.amhs.AMHSRailNet;
import com.gregtechceu.gtceu.api.pipenet.amhs.AMHSRailType;
import com.gregtechceu.gtceu.api.pipenet.amhs.LevelAMHSRailNet;
import com.gregtechceu.gtceu.api.pipenet.amhs.RailConnection;
import com.gregtechceu.gtceu.client.renderer.block.RailBlockRenderer;
import com.gregtechceu.gtceu.common.item.CoverPlaceBehavior;
import com.lowdragmc.lowdraglib.LDLib;
import com.lowdragmc.lowdraglib.client.renderer.IBlockRendererProvider;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;
import com.lowdragmc.lowdraglib.pipelike.Node;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.gregtechceu.gtceu.api.pipenet.amhs.RailConnection.*;
import static com.gregtechceu.gtceu.api.pipenet.amhs.RailConnection.STRAIGHT;

/**
 * @author KilaBash
 * @date 2023/8/4
 * @implNote RailBlock
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AMHSRailBlock extends AppearanceBlock implements IBlockRendererProvider, IToolGridHighLight, IToolable {
    public static final EnumProperty<Direction> DIRECTION = EnumProperty.create("direction", Direction.class, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST);

    public final AMHSRailType railType;
    protected IRenderer renderer;

    public AMHSRailBlock(Properties properties, AMHSRailType railType) {
        super(railType.onProperties(properties));
        var defaultState = defaultBlockState();
        if (railType.connectionProperty != null) {
            defaultState = defaultState.setValue(railType.connectionProperty, RailConnection.STRAIGHT);
        }
        registerDefaultState(defaultState.setValue(DIRECTION, Direction.SOUTH));
        this.railType = railType;
    }

    public void onRegister() {
        if (LDLib.isClient()) {
            this.renderer = createRenderer();
        } else {
            this.renderer = null;
        }
    }

    public IRenderer createRenderer() {
        return new RailBlockRenderer(railType);
    }

    public RailConnection getRailConnection(BlockState state) {
        if (railType.connectionProperty == null) {
            return railType.railConnections.iterator().next();
        }
        return state.getValue(railType.connectionProperty);
    }

    public BlockState setRailConnection(BlockState state, RailConnection connection) {
        if (railType.connectionProperty == null) {
            return state;
        }
        if (railType.railConnections.contains(connection)) {
            return state.setValue(railType.connectionProperty, connection);
        }
        return state;
    }

    public Direction getRailDirection(BlockState state) {
        return state.getValue(DIRECTION);
    }

    public BlockState setRailDirection(BlockState state, Direction direction) {
        return state.setValue(DIRECTION, direction);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        var type = AMHSRailType.get();
        if (type.connectionProperty != null) {
            builder = builder.add(type.connectionProperty);
        }
        super.createBlockStateDefinition(builder.add(DIRECTION, BlockProperties.SERVER_TICK));
    }

    public LevelAMHSRailNet getWorldRailNet(ServerLevel level) {
        return LevelAMHSRailNet.getOrCreate(level);
    }

    @Override
    public @Nullable IRenderer getRenderer(BlockState state) {
        return renderer;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var state = this.defaultBlockState();
        //find connected rail
        for (var side : AMHSRailNet.VALUES) {
            var neighbour = context.getLevel().getBlockState(context.getClickedPos().relative(side));
            if (neighbour.getBlock() instanceof AMHSRailBlock railBlock) {
                var con = railBlock.getRailConnection(neighbour);
                var dir = railBlock.getRailDirection(neighbour);
                var io = con.getIO(dir, side.getOpposite());
                if (io == IO.IN) {
                    return setRailDirection(setRailConnection(state, RailConnection.STRAIGHT), side.getOpposite());
                } else if (io == IO.OUT) {
                    return setRailDirection(setRailConnection(state, RailConnection.STRAIGHT), side);
                }
            }
        }
        return state;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (level instanceof ServerLevel serverLevel) {
            var net = getWorldRailNet(serverLevel);
            if (net.getNetFromPos(pos) == null) {
                net.addNode(pos, railType, getRailConnection(state), getRailDirection(state), Node.DEFAULT_MARK, true);
            } else {
                net.updateNodeConnections(pos, getRailConnection(state), getRailDirection(state));
            }
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            pLevel.updateNeighbourForOutputSignal(pPos, this);
            if (pState.hasBlockEntity()) {
                pLevel.removeBlockEntity(pPos);
            }
            if (pLevel instanceof ServerLevel serverLevel) {
                getWorldRailNet(serverLevel).removeNode(pPos);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityCtx && entityCtx.getEntity() instanceof Player player){
            var held = player.getMainHandItem();
            if (held.is(GTToolType.WRENCH.itemTag)) {
                return Shapes.block();
            }
        }
        return getRailConnection(pState).getShape(getRailDirection(pState));
    }

    //////////////////////////////////////
    //*******     Interaction    *******//
    //////////////////////////////////////
    @Override
    public boolean shouldRenderGrid(Player player, BlockPos pos, BlockState state, ItemStack held, GTToolType toolType) {
        return canToolTunePipe(toolType);
    }

    @Override
    public ResourceTexture sideTips(Player player, BlockPos pos, BlockState state, GTToolType toolType, Direction side) {
        if (canToolTunePipe(toolType) && side.getAxis() != Direction.Axis.Y) {
            if (player.isCrouching()) {
                var direction = getRailDirection(state);
                if (direction != side) {
                    return GuiTextures.TOOL_FRONT_FACING_ROTATION;
                }
            } else {
                var direction = getRailDirection(state);
                if(side.getAxis() != direction.getAxis()) {
                    return GuiTextures.TOOL_IO_FACING_ROTATION;
                }
            }
        }
        return null;
    }

    @Override
    public InteractionResult onToolClick(@NotNull GTToolType toolType, ItemStack itemStack, UseOnContext context) {
        // the side hit from the machine grid
        var playerIn = context.getPlayer();
        if (playerIn == null) return InteractionResult.PASS;
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var hitResult = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), pos, false);
        Direction gridSide = ICoverable.determineGridSideHit(hitResult);
        if (gridSide == null) gridSide = hitResult.getDirection();

        var state = level.getBlockState(pos);
        if (canToolTunePipe(toolType) && gridSide.getAxis() != Direction.Axis.Y) {
            if (playerIn.isCrouching()) {
                var direction = getRailDirection(state);
                if (direction != gridSide) {
                    if (!level.isClientSide) {
                        level.setBlockAndUpdate(context.getClickedPos(), state = setRailDirection(state, gridSide));
                    }
                    return InteractionResult.CONSUME;
                }
            } else {
                var direction = getRailDirection(state);
                if(gridSide.getAxis() != direction.getAxis()) {
                    if (!level.isClientSide) {
                        boolean isLeft = direction.getClockWise() == gridSide;
                        RailConnection nextConnection = switch (getRailConnection(state)) {
                            case STRAIGHT -> isLeft ? LEFT : RIGHT;
                            case LEFT -> isLeft ? STRAIGHT_LEFT_IN : RIGHT;
                            case RIGHT -> isLeft ? LEFT : STRAIGHT_RIGHT_IN;
                            case STRAIGHT_LEFT_IN -> isLeft ? STRAIGHT_LEFT_OUT : STRAIGHT_RIGHT_IN;
                            case STRAIGHT_RIGHT_IN -> isLeft ? STRAIGHT_LEFT_IN : STRAIGHT_RIGHT_OUT;
                            case STRAIGHT_LEFT_OUT -> isLeft ? STRAIGHT : STRAIGHT_RIGHT_OUT;
                            case STRAIGHT_RIGHT_OUT -> isLeft ? STRAIGHT_LEFT_OUT : STRAIGHT;
                        };
                        level.setBlockAndUpdate(context.getClickedPos(), state = setRailConnection(state, nextConnection));
                    }
                    return InteractionResult.CONSUME;
                }
            }
        }

        return InteractionResult.PASS;
    }

    protected boolean canToolTunePipe(GTToolType toolType) {
        return toolType == GTToolType.WRENCH;
    }

}
