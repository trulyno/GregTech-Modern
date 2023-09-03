package com.gregtechceu.gtceu.common.blockentity;

import com.gregtechceu.gtceu.api.entity.FOUPCartEntity;
import dev.architectury.injectables.annotations.ExpectPlatform;
import lombok.Setter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;


/**
 * @author KilaBash
 * @date 2023/3/1
 * @implNote CableBlockEntity
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FOUPRailBlockEntity extends BlockEntity {

    protected FOUPRailBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @ExpectPlatform
    public static FOUPRailBlockEntity create(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void onBlockEntityRegister(BlockEntityType<FOUPRailBlockEntity> type) {
        throw new AssertionError();
    }

    @Setter
    public FOUPCartEntity awaitedCart = null;

    @Nullable
    public FOUPCartEntity getAwaitedCart() {
        if (awaitedCart == null) return null;
        if (!awaitedCart.isAwaiting() || !getBlockPos().equals(awaitedCart.getAwaitedPos().orElse(null))) {
            awaitedCart = null;
        }
        return awaitedCart;
    }

}
