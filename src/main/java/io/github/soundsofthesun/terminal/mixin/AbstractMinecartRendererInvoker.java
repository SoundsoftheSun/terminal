package io.github.soundsofthesun.terminal.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.AbstractMinecartRenderer;
import net.minecraft.client.renderer.entity.state.MinecartRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractMinecartRenderer.class)
public interface AbstractMinecartRendererInvoker {
    @Invoker("newRender")
    static <S extends MinecartRenderState> void terminal$newRender(S minecartRenderState, PoseStack poseStack) {

    }

    @Invoker("oldRender")
    static <S extends MinecartRenderState> void terminal$oldRender(S minecartRenderState, PoseStack poseStack) {

    }
}
