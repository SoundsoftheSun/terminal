package io.github.soundsofthesun.terminal.block.terminalblock;

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

public class TerminalBlockEntityRenderer implements BlockEntityRenderer<TerminalBlockEntity, TerminalBlockEntityRenderState> {
    public static void initialize() {
        BlockEntityRenderers.register(TBlockEntities.TERMINAL_BLOCK_ENTITY, TerminalBlockEntityRenderer::new);
    }

    public TerminalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public TerminalBlockEntityRenderState createRenderState() {
        return new TerminalBlockEntityRenderState();
    }

//    @Override
//    public void updateRenderState(TerminalBlockEntity blockEntity, TerminalBlockEntityRenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
//        BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
//    }

    @Override
    public void submit(TerminalBlockEntityRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
    }
}
