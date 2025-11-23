package io.github.soundsofthesun.terminal.block.terminalblock;

import io.github.soundsofthesun.terminal.block.TBlockEntities;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class TerminalBlockEntityRenderer implements BlockEntityRenderer<TerminalBlockEntity, TerminalBlockEntityRenderState> {
    public static void initialize() {
        BlockEntityRendererFactories.register(TBlockEntities.TERMINAL_BLOCK_ENTITY, TerminalBlockEntityRenderer::new);
    }

    public TerminalBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public TerminalBlockEntityRenderState createRenderState() {
        return new TerminalBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(TerminalBlockEntity blockEntity, TerminalBlockEntityRenderState state, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
    }

    @Override
    public void render(TerminalBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
    }
}
