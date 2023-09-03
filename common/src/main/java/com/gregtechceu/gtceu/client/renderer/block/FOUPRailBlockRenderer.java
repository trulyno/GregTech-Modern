package com.gregtechceu.gtceu.client.renderer.block;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.block.AMHSRailBlock;
import com.gregtechceu.gtceu.common.blockentity.FOUPRailBlockEntity;
import com.lowdragmc.lowdraglib.client.renderer.impl.IModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

/**
 * @author KilaBash
 * @date 2023/8/13
 * @implNote FOUPRailBlockRenderer
 */
public class FOUPRailBlockRenderer extends IModelRenderer {

    public FOUPRailBlockRenderer() {
        super(GTCEu.id("block/amhs/foup/straight"));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean hasTESR(BlockEntity blockEntity) {
        return true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRender(BlockEntity blockEntity, Vec3 cameraPos) {
        return super.shouldRender(blockEntity, cameraPos);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void render(BlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        if (blockEntity instanceof FOUPRailBlockEntity rail && rail.getBlockState().getBlock() instanceof AMHSRailBlock railBlock) {
            poseStack.pushPose();

            var cart = rail.getAwaitedCart();
            if (cart != null) {
                var dir = railBlock.getRailDirection(blockEntity.getBlockState());
                var degree = cart.getAwaitingDegree(partialTicks);
                poseStack.translate(0.5, 1, 0.5);
                if (dir.getAxis() == Direction.Axis.X) {
                    poseStack.mulPose(Vector3f.XP.rotationDegrees(degree));
                } else if (dir.getAxis() == Direction.Axis.Z) {
                    poseStack.mulPose(Vector3f.ZP.rotationDegrees(degree));
                }
                poseStack.translate(-0.5, -1, -0.5);
            }

            var bakedmodel = getRotatedModel(railBlock.getRailDirection(blockEntity.getBlockState()).getOpposite());
            Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(
                    poseStack.last(),
                    buffer.getBuffer(Sheets.cutoutBlockSheet()),
                    blockEntity.getBlockState(),
                    bakedmodel,
                    1,
                    1,
                    1,
                    combinedLight,
                    combinedOverlay);
            poseStack.popPose();
        }
    }
}
