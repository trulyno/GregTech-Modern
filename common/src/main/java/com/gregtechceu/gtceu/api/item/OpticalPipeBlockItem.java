package com.gregtechceu.gtceu.api.item;

import com.gregtechceu.gtceu.api.block.PipeBlock;
import com.gregtechceu.gtceu.common.block.LaserPipeBlock;
import com.gregtechceu.gtceu.common.block.OpticalPipeBlock;
import com.lowdragmc.lowdraglib.client.renderer.IItemRendererProvider;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class OpticalPipeBlockItem extends PipeBlockItem implements IItemRendererProvider {
    public OpticalPipeBlockItem(PipeBlock block, Properties properties) {
        super(block, properties);
    }

    @Override
    public OpticalPipeBlock getBlock() {
        return (OpticalPipeBlock) super.getBlock();
    }

    @Environment(EnvType.CLIENT)
    public static ItemColor tintColor() {
        return (itemStack, index) -> {
            if (itemStack.getItem() instanceof OpticalPipeBlockItem materialBlockItem) {
                //return materialBlockItem.getBlock().tinted(materialBlockItem.getBlock().defaultBlockState(), null, null, index);
            }
            return -1;
        };
    }

    @Nullable
    @Override
    @Environment(EnvType.CLIENT)
    public IRenderer getRenderer(ItemStack stack) {
        return getBlock().getRenderer(getBlock().defaultBlockState());
    }
}