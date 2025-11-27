package io.github.soundsofthesun.terminal.entity.goldminecart;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.mixin.AbstractMinecartRendererInvoker;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.AbstractMinecartRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.MinecartRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GoldMinecartRenderer extends AbstractMinecartRenderer<AbstractMinecart, GoldMinecartRenderState> {
    public static MinecartModel model;

    public GoldMinecartRenderer(EntityRendererProvider.Context context) {
        super(context, GoldMinecartModel.GOLD_MINECART_LOCATION);
        this.model = new MinecartModel(context.bakeLayer(GoldMinecartModel.GOLD_MINECART_LOCATION));
    }

    private ResourceLocation MINECART = ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, "textures/entity/gold_minecart.png");

    @Override
    public GoldMinecartRenderState createRenderState() {
        return new GoldMinecartRenderState();
    }

    @Override
    public void submit(GoldMinecartRenderState minecartRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        super.submit(minecartRenderState, poseStack, submitNodeCollector, cameraRenderState);
        poseStack.pushPose();
        long l = minecartRenderState.offsetSeed;
        float f = (((float)(l >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float g = (((float)(l >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float h = (((float)(l >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        poseStack.translate(f, g, h);

//        if (minecartRenderState.isNewRender) {
//            newRender(minecartRenderState, poseStack);
//        } else {
//            oldRender(minecartRenderState, poseStack);
//        }

        if (minecartRenderState.isNewRender) {
            AbstractMinecartRendererInvoker.terminal$newRender(minecartRenderState, poseStack);
        } else {
            AbstractMinecartRendererInvoker.terminal$oldRender(minecartRenderState, poseStack);
        }


        float i = minecartRenderState.hurtTime;
        if (i > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(i) * i * minecartRenderState.damageTime / 10.0F * (float)minecartRenderState.hurtDir));
        }

        BlockState blockState = minecartRenderState.displayBlockState;
        if (blockState.getRenderShape() != RenderShape.INVISIBLE) {
            poseStack.pushPose();
            poseStack.scale(0.75F, 0.75F, 0.75F);
            poseStack.translate(-0.5F, (float)(minecartRenderState.displayOffset - 8) / 16.0F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
            this.submitMinecartContents(minecartRenderState, blockState, poseStack, submitNodeCollector, minecartRenderState.lightCoords);
            poseStack.popPose();
        }

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        submitNodeCollector.submitModel(this.model, minecartRenderState, poseStack, this.model.renderType(MINECART), minecartRenderState.lightCoords, OverlayTexture.NO_OVERLAY, minecartRenderState.outlineColor, (ModelFeatureRenderer.CrumblingOverlay)null);
        poseStack.popPose();
    }

    private static <S extends MinecartRenderState> void newRender(S minecartRenderState, PoseStack poseStack) {
        poseStack.mulPose(Axis.YP.rotationDegrees(minecartRenderState.yRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-minecartRenderState.xRot));
        poseStack.translate(0.0F, 0.375F, 0.0F);
    }

    private static <S extends MinecartRenderState> void oldRender(S minecartRenderState, PoseStack poseStack) {
        double d = minecartRenderState.x;
        double e = minecartRenderState.y;
        double f = minecartRenderState.z;
        float g = minecartRenderState.xRot;
        float h = minecartRenderState.yRot;
        if (minecartRenderState.posOnRail != null && minecartRenderState.frontPos != null && minecartRenderState.backPos != null) {
            Vec3 vec3 = minecartRenderState.frontPos;
            Vec3 vec32 = minecartRenderState.backPos;
            poseStack.translate(minecartRenderState.posOnRail.x - d, (vec3.y + vec32.y) / (double)2.0F - e, minecartRenderState.posOnRail.z - f);
            Vec3 vec33 = vec32.add(-vec3.x, -vec3.y, -vec3.z);
            if (vec33.length() != (double)0.0F) {
                vec33 = vec33.normalize();
                h = (float)(Math.atan2(vec33.z, vec33.x) * (double)180.0F / Math.PI);
                g = (float)(Math.atan(vec33.y) * (double)73.0F);
            }
        }

        poseStack.translate(0.0F, 0.375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - h));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-g));
    }

}
