package com.gregtechceu.gtceu.client.renderer.block;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.block.AMHSRailBlock;
import com.gregtechceu.gtceu.api.pipenet.amhs.AMHSRailType;
import com.gregtechceu.gtceu.api.pipenet.amhs.RailConnection;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import com.lowdragmc.lowdraglib.client.renderer.impl.IModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.List;

/**
 * @author KilaBash
 * @date 2023/3/1
 * @implNote RailBlockRenderer
 */
public class RailBlockRenderer implements IRenderer {

    protected EnumMap<RailConnection, IModelRenderer> models;

    public RailBlockRenderer(AMHSRailType type) {
        this.models = new EnumMap<>(RailConnection.class);
        for (RailConnection connection : RailConnection.values()) {
            models.put(connection, new IModelRenderer(GTCEu.id("block/amhs/%s/%s".formatted(type.name, connection.name))));
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void renderItem(ItemStack stack,
                           ItemTransforms.TransformType transformType,
                           boolean leftHand, PoseStack matrixStack,
                           MultiBufferSource buffer, int combinedLight,
                           int combinedOverlay, BakedModel model) {
        models.get(RailConnection.STRAIGHT).renderItem(stack, transformType, leftHand, matrixStack, buffer, combinedLight, combinedOverlay, model);
    }

    @Override
    public boolean useAO() {
        return true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean useBlockLight(ItemStack stack) {
        return true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public List<BakedQuad> renderModel(BlockAndTintGetter level, BlockPos pos, BlockState state, Direction side, RandomSource rand) {
        if (state == null || !(state.getBlock() instanceof AMHSRailBlock railBlock)) {
            return models.get(RailConnection.STRAIGHT).renderModel(level, pos, null, side, rand);
        }
        return models.get(railBlock.getRailConnection(state))
                .getRotatedModel(railBlock.getRailDirection(state).getOpposite()).getQuads(state, side, rand);
    }

    @NotNull
    @Override
    @Environment(EnvType.CLIENT)
    public TextureAtlasSprite getParticleTexture() {
        return models.get(RailConnection.STRAIGHT).getParticleTexture();
    }

}
