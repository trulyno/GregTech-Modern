package com.gregtechceu.gtceu.client.renderer.block;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.common.block.FOUPFunnelBlock;
import com.lowdragmc.lowdraglib.client.model.ModelFactory;
import com.lowdragmc.lowdraglib.client.renderer.impl.IModelRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author KilaBash
 * @date 2023/8/14
 * @implNote FOUPFunnelRenderer
 */
public class FOUPFunnelRenderer extends IModelRenderer {
    public static final FOUPFunnelRenderer INSTANCE = new FOUPFunnelRenderer();

    protected FOUPFunnelRenderer() {
        super(GTCEu.id("block/amhs/funnel/foup_funnel"));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public List<BakedQuad> renderModel(@Nullable BlockAndTintGetter level, @Nullable BlockPos pos, @Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        var rotation = Direction.DOWN;
        if (state != null) {
            rotation = state.getValue(FOUPFunnelBlock.FACING);
        }
        return getRotatedModel(rotation).getQuads(state, side, rand);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public BakedModel getRotatedModel(Direction frontFacing) {
        return blockModels.computeIfAbsent(frontFacing, facing -> getModel().bake(
                ModelFactory.getModeBakery(),
                this::materialMapping,
                switch (facing) {
                    case DOWN -> BlockModelRotation.X0_Y0;
                    case UP -> BlockModelRotation.X180_Y0;
                    case NORTH -> BlockModelRotation.X90_Y0;
                    case SOUTH -> BlockModelRotation.X270_Y0;
                    case WEST -> BlockModelRotation.X90_Y270;
                    case EAST -> BlockModelRotation.X90_Y90;
                },
                modelLocation));
    }
}
