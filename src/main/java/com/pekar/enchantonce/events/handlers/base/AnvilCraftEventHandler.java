package com.pekar.enchantonce.events.handlers.base;

import com.pekar.enchantonce.events.handlers.craft.AnvilCraftEventWrapper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.AnvilRepairEvent;

public abstract class AnvilCraftEventHandler extends AnvilEventHandler<AnvilRepairEvent>
{
    protected ItemStack leftItemStack;
    protected ItemStack rightItemStack;
    protected AnvilCraftEventWrapper event;

    @Override
    public boolean tryHandle(AnvilRepairEvent event)
    {
        this.event = new AnvilCraftEventWrapper(event);
        rightItemStack = event.getRight();
        leftItemStack = event.getLeft();

        return super.tryHandle(event);
    }
}
