package com.pekar.enchantonce.events.handlers.craft;

import com.pekar.enchantonce.events.AnvilCraftPostEvent;
import net.minecraft.world.entity.player.Player;

public class AnvilCraftEventWrapper
{
    private final AnvilCraftPostEvent event;

    public AnvilCraftEventWrapper(AnvilCraftPostEvent event)
    {
        this.event = event;
    }

    public Player getPlayer()
    {
        return event.getPlayer();
    }
}
