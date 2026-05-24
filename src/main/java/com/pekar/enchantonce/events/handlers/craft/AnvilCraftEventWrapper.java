package com.pekar.enchantonce.events.handlers.craft;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.AnvilRepairEvent;

public class AnvilCraftEventWrapper
{
    private final AnvilRepairEvent event;

    public AnvilCraftEventWrapper(AnvilRepairEvent event)
    {
        this.event = event;
    }

    public ItemStack getOutput()
    {
        return event.getOutput();
    }

    public Player getPlayer()
    {
        return event.getEntity();
    }
}
