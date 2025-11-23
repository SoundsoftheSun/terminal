package io.github.soundsofthesun.terminal.block.station;

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

public class StationBlockEntityRenderer implements BlockEntityRenderer<StationBlockEntity, StationBlockEntityRenderState> {
    public static void initialize() {
        BlockEntityRenderers.register(TBlockEntities.STATION_BLOCK_ENTITY, StationBlockEntityRenderer::new);
    }

    public StationBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public StationBlockEntityRenderState createRenderState() {
        return new StationBlockEntityRenderState();
    }

//    @Override
//    public void updateRenderState(StationBlockEntity blockEntity, StationBlockEntityRenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
//        BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
//    }

    @Override
    public void submit(StationBlockEntityRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
    }
}
