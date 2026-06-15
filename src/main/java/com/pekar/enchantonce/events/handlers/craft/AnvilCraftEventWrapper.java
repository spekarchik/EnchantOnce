package com.pekar.enchantonce.events.handlers.craft;

import com.pekar.enchantonce.events.AnvilCraftPreEvent;
import net.minecraft.world.entity.player.Player;

public class AnvilCraftEventWrapper
{
    private final AnvilCraftPreEvent event;

    public AnvilCraftEventWrapper(AnvilCraftPreEvent event)
    {
        this.event = event;
    }

    public Player getPlayer()
    {
        return event.getPlayer();
    }
}
