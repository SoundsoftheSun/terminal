package io.github.soundsofthesun.terminal.item;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.item.items.TerminalController;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class TItems {
    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {

        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Terminal.MOD_ID, name));

        Item item = itemFactory.apply(settings.registryKey(itemKey));

        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {

        // Add to item groups
        ItemGroupEvents.modifyEntriesEvent(Terminal.TERMINAL_GROUP_KEY)
                .register((itemGroup) -> {
                    itemGroup.add(TItems.TERMINAL_CONTROLLER);
                });

        // Tooltip translation keys
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.isOf(TItems.TERMINAL_CONTROLLER)) list.add(Text.translatable("itemTooltip.terminal.terminal_controller"));
        });

    }

    public static final Item TERMINAL_CONTROLLER = register("terminal_controller", TerminalController::new, new Item.Settings()
            .maxCount(1)
    );

}
