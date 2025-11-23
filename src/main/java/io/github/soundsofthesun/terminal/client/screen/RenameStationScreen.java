package io.github.soundsofthesun.terminal.client.screen;

import io.github.soundsofthesun.terminal.network.c2s.StationRenameC2SPayload;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.TextBoxComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class RenameStationScreen extends BaseOwoScreen<FlowLayout> {

    String name;
    BlockPos pos;

    public RenameStationScreen(String name, BlockPos pos) {
        this.pos = pos;
        this.name = name;
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout layout) {
        layout
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER)
        ;


        TextBoxComponent textBox = Components.textBox(Sizing.fill(50), name);

        layout.child(Containers.grid(Sizing.content(), Sizing.content(), 1, 1)
                .child(textBox, 0, 0)
        );

        layout.child(Components.button(Component.literal("Rename"), buttonComponent -> {
            System.out.println("RENAME to "+textBox.getValue());

            StationRenameC2SPayload payload = new StationRenameC2SPayload(textBox.getValue(), this.pos);
            ClientPlayNetworking.send(payload);
            this.onClose();

        }));






    }
}
