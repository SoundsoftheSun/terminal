package io.github.soundsofthesun.terminal.item.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TerminalController extends Item {
    public TerminalController(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {

        if (world.isClient()) return;

        if (!(entity instanceof PlayerEntity playerEntity)) return;

        if (!(slot == EquipmentSlot.MAINHAND)) return;

    }

    @Override
    public ActionResult use(World world, PlayerEntity entity, Hand hand) {

        return ActionResult.SUCCESS;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

}
