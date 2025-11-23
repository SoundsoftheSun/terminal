package io.github.soundsofthesun.terminal.block.switchblock;


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

public class SwitchBlockEntityRenderer implements BlockEntityRenderer<SwitchBlockEntity, SwitchBlockEntityRenderState> {
    public static void initialize() {
        BlockEntityRendererFactories.register(TBlockEntities.SWITCH_BLOCK_ENTITY, SwitchBlockEntityRenderer::new);
    }

    public SwitchBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public SwitchBlockEntityRenderState createRenderState() {
        return new SwitchBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(SwitchBlockEntity blockEntity, SwitchBlockEntityRenderState state, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
    }

    @Override
    public void render(SwitchBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
    }
}