package com.pekar.enchantonce.events.handlers.base;

import com.pekar.enchantonce.events.AnvilCraftPostEvent;
import com.pekar.enchantonce.events.handlers.craft.AnvilCraftEventWrapper;
import net.minecraft.world.item.ItemStack;

public abstract class AnvilCraftEventHandler extends AnvilEventHandler<AnvilCraftPostEvent>
{
    protected ItemStack leftItemStack;
    protected ItemStack rightItemStack;
    protected AnvilCraftEventWrapper event;

    @Override
    public boolean tryHandle(AnvilCraftPostEvent event)
    {
        this.event = new AnvilCraftEventWrapper(event);
        rightItemStack = event.getRight();
        leftItemStack = event.getLeft();

        return super.tryHandle(event);
    }
}
