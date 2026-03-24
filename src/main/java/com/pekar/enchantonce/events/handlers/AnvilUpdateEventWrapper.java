package com.pekar.enchantonce.events.handlers;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

public class AnvilUpdateEventWrapper
{
    private final AnvilUpdateEvent event;

    public AnvilUpdateEventWrapper(AnvilUpdateEvent event)
    {
        this.event = event;
    }

    public Level getLevel()
    {
        return event.getPlayer().level();
    }

    public ItemStack getOutput()
    {
        return event.getOutput();
    }

    public void setOutput(ItemStack output)
    {
        event.setOutput(output);
    }

    public void setXpCost(int xpCost)
    {
        event.setXpCost(xpCost);
    }

    public int getMaterialCost()
    {
        return event.getMaterialCost();
    }

    public void setMaterialCost(int materialCost)
    {
        event.setMaterialCost(materialCost);
    }

    public void cancel()
    {
        event.setCanceled(true);
    }
}
