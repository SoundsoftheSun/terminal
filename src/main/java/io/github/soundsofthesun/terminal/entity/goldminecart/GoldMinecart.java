package io.github.soundsofthesun.terminal.entity.goldminecart;

import io.github.soundsofthesun.terminal.item.TItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GoldMinecart extends Minecart {
    public GoldMinecart(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public ItemStack getPickResult() {
        return TItems.GOLD_MINECART_ITEM.getDefaultInstance();
    }

    @Override
    protected Item getDropItem() {
        return TItems.GOLD_MINECART_ITEM;
    }
}
