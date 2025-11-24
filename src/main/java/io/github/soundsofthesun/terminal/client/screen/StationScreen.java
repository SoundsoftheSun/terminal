package io.github.soundsofthesun.terminal.client.screen;

import io.github.soundsofthesun.terminal.network.c2s.StationTransitC2SPayload;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.ScrollContainer;
import io.wispforest.owo.ui.core.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StationScreen extends BaseOwoScreen<FlowLayout> {

    Component text;
    LinkedHashMap<Long, String> posMap;
    List<io.wispforest.owo.ui.core.Component> components = new ArrayList<>();
    BlockPos source;

    public StationScreen(LinkedHashMap<Long, String> posMap, BlockPos source) { //TODO sorting stations on screen
        this.posMap = posMap;
        this.source = source;
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

        layout.child(
                Components.label(Component.literal(this.posMap.get(this.source.asLong())))
                        .shadow(true)
        );

        // Add source station first
        components.add(
                Containers.grid(Sizing.fill(), Sizing.content(), 3, 5)
                        .child(Components.label(Component.literal(this.posMap.get(this.source.asLong())))
                                .shadow(true).lineHeight(10), 0, 0)
                        .child(Components.label(Component.literal(this.source.getX()+" "+this.source.getY()+" "+this.source.getZ()))
                                .shadow(true), 1, 0)
                        .child(Components.label(Component.literal("0 Blocks Away"))
                                .shadow(true), 2, 0)
                        .child(Components.button(Component.literal("Rename"), button -> {
                            RenameStationScreen screen = new RenameStationScreen(this.posMap.get(this.source.asLong()), this.source);
                            Minecraft.getInstance().setScreen(screen);
                        }), 1, 3)
                        .child(Components.button(Component.literal("Transit"), button -> {

                        }).active(false), 1, 4)
                        .padding(Insets.of(6))
                        .surface(Surface.DARK_PANEL)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .margins(Insets.of(2))


        );

        for (Map.Entry<Long, String> entry : this.posMap.entrySet()) {
            // Ignore source
            if (entry.getKey() == this.source.asLong()) continue;
            BlockPos pos = BlockPos.of(entry.getKey());
            components.add(
                    Containers.grid(Sizing.fill(), Sizing.content(), 3, 5)
                            .child(Components.label(Component.literal(entry.getValue()))
                                    .shadow(true).lineHeight(10), 0, 0)
                            .child(Components.label(Component.literal(pos.getX()+" "+pos.getY()+" "+pos.getZ()))
                                    .shadow(true), 1, 0)
                            .child(Components.label(Component.literal(
                                                    (int) Math.sqrt(
                                            (
                                                    (pos.getX()-source.getX()) * (pos.getX()-source.getX())
                                                    )
                                            +
                                            (
                                                    (pos.getY()-source.getY()) * (pos.getY()-source.getY())
                                                    )
                                            +
                                            (
                                                    (pos.getZ()-source.getZ()) * (pos.getZ()-source.getZ())
                                                    )
                                    )+" Blocks Away"
                                    ))
                                    .shadow(true), 2, 0)
                            .child(Components.button(Component.literal("Rename"), button -> {
                                RenameStationScreen screen = new RenameStationScreen(entry.getValue(), pos);
                                Minecraft.getInstance().setScreen(screen);
                            }), 1, 3)
                            .child(Components.button(Component.literal("Transit"), button -> {
                                StationTransitC2SPayload payload = new StationTransitC2SPayload(this.source, pos);
                                ClientPlayNetworking.send(payload);
                                this.onClose();
                            }), 1, 4)
                            .padding(Insets.of(6))
                            .surface(Surface.DARK_PANEL)
                            .verticalAlignment(VerticalAlignment.CENTER)
                            .margins(Insets.of(2))
            );
        }

        layout.child(Containers.verticalScroll(Sizing.fill(60), Sizing.fill(60), Containers.verticalFlow(Sizing.content(), Sizing.content())
                .children(components))
                .scrollbarThiccness(4)
                .scrollbar(ScrollContainer.Scrollbar.vanillaFlat())
                .padding(Insets.of(2))
                .surface(Surface.VANILLA_TRANSLUCENT)
        );
    }
}
