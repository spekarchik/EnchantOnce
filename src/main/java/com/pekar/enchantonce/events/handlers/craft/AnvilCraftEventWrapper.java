package com.pekar.enchantonce.events.handlers.craft;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.AnvilRepairEvent;

public class AnvilCraftEventWrapper
{
    private final AnvilRepairEvent event;

    public AnvilCraftEventWrapper(AnvilRepairEvent event)
    {
        this.event = event;
    }

    public Player getPlayer()
    {
        return event.getEntity();
    }
}
