package com.gregtechceu.gtceu.common.blockentity.fabric;

import com.gregtechceu.gtceu.common.blockentity.FOUPFunnelBlockEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author KilaBash
 * @date 2023/8/14
 * @implNote FOUPFunnelBlockEntityImpl
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FOUPFunnelBlockEntityImpl extends FOUPFunnelBlockEntity {
    protected FOUPFunnelBlockEntityImpl(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public static FOUPFunnelBlockEntity create(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        return new FOUPFunnelBlockEntityImpl(type, pos, blockState);
    }

    public static void onBlockEntityRegister(BlockEntityType<FOUPFunnelBlockEntity> type) {
    }
}
