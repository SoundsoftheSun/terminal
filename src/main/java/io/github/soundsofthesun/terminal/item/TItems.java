package io.github.soundsofthesun.terminal.item;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.item.items.TerminalController;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import java.util.function.Function;

public class TItems {
    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {

        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, name));

        Item item = itemFactory.apply(settings.setId(itemKey));

        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {
        // Add to item groups
        ItemGroupEvents.modifyEntriesEvent(Terminal.TERMINAL_GROUP_KEY)
                .register((itemGroup) -> {
                    itemGroup.accept(TItems.TERMINAL_CONTROLLER);
                });
    }

    public static void initializeClient() {
        // Tooltip translation keys
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.is(TItems.TERMINAL_CONTROLLER)) list.add(Component.translatable("itemTooltip.terminal.terminal_controller"));
        });
    }

    public static final Item TERMINAL_CONTROLLER = register("terminal_controller", TerminalController::new, new Item.Properties()
            .stacksTo(1)
    );

}
