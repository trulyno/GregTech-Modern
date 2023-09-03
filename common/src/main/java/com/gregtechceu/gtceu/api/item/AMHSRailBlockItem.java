package com.gregtechceu.gtceu.api.item;

import com.gregtechceu.gtceu.api.block.AMHSRailBlock;
import com.lowdragmc.lowdraglib.client.renderer.IItemRendererProvider;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author KilaBash
 * @implNote MaterialBlockItem
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AMHSRailBlockItem extends BlockItem implements IItemRendererProvider {

    public AMHSRailBlockItem(AMHSRailBlock block, Properties properties) {
        super(block, properties);
    }

    @Override
    @Nonnull
    public AMHSRailBlock getBlock() {
        return (AMHSRailBlock)super.getBlock();
    }

    @Nullable
    @Override
    @Environment(EnvType.CLIENT)
    public IRenderer getRenderer(ItemStack stack) {
        return getBlock().getRenderer(getBlock().defaultBlockState());
    }

    @Override
    public Component getDescription() {
        return this.getBlock().getName();
    }

    @Override
    public Component getName(ItemStack stack) {
        return getDescription();
    }
}
