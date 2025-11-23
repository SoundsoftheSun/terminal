package io.github.soundsofthesun.terminal.block.station;

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

public class StationBlockEntityRenderer implements BlockEntityRenderer<StationBlockEntity, StationBlockEntityRenderState> {
    public static void initialize() {
        BlockEntityRendererFactories.register(TBlockEntities.STATION_BLOCK_ENTITY, StationBlockEntityRenderer::new);
    }

    public StationBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public StationBlockEntityRenderState createRenderState() {
        return new StationBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(StationBlockEntity blockEntity, StationBlockEntityRenderState state, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
    }

    @Override
    public void render(StationBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
    }
}
