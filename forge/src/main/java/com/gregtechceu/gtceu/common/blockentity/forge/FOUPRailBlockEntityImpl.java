package com.gregtechceu.gtceu.common.blockentity.forge;

import com.gregtechceu.gtceu.common.blockentity.FOUPRailBlockEntity;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author KilaBash
 * @date 2023/8/8
 * @implNote AMHSRailBlockEntityImpl
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FOUPRailBlockEntityImpl extends FOUPRailBlockEntity {
    protected FOUPRailBlockEntityImpl(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public static FOUPRailBlockEntity create(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        return new FOUPRailBlockEntityImpl(type, pos, blockState);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }

    public static void onBlockEntityRegister(BlockEntityType<FOUPRailBlockEntity> type) {
    }

    @Override
    public AABB getRenderBoundingBox() {
        return super.getRenderBoundingBox();
    }
}
