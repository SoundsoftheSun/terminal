package io.github.soundsofthesun.terminal.block.switchblock;


import com.mojang.blaze3d.vertex.PoseStack;
import io.github.soundsofthesun.terminal.block.TBlockEntities;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SwitchBlockEntityRenderer implements BlockEntityRenderer<SwitchBlockEntity, SwitchBlockEntityRenderState> {
    public static void initialize() {
        BlockEntityRenderers.register(TBlockEntities.SWITCH_BLOCK_ENTITY, SwitchBlockEntityRenderer::new);
    }

    public SwitchBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public SwitchBlockEntityRenderState createRenderState() {
        return new SwitchBlockEntityRenderState();
    }

//    @Override
//    public void updateRenderState(SwitchBlockEntity blockEntity, SwitchBlockEntityRenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
//        BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
//    }

    @Override
    public void submit(SwitchBlockEntityRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
    }
}