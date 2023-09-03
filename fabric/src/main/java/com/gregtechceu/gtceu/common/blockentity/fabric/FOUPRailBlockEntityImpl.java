package com.gregtechceu.gtceu.common.blockentity.fabric;

import com.gregtechceu.gtceu.common.blockentity.FOUPRailBlockEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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

    public static void onBlockEntityRegister(BlockEntityType<FOUPRailBlockEntity> type) {
    }
}
