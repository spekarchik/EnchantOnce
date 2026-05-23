package com.pekar.enchantonce.events.handlers.craft;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.AnvilCraftEvent;

public class AnvilCraftEventWrapper
{
    private final AnvilCraftEvent event;

    public AnvilCraftEventWrapper(AnvilCraftEvent event)
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
