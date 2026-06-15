package com.pekar.enchantonce.events.handlers.base;

import com.pekar.enchantonce.events.AnvilCraftPreEvent;
import com.pekar.enchantonce.events.handlers.craft.AnvilCraftEventWrapper;
import net.minecraft.world.item.ItemStack;

public abstract class AnvilCraftEventHandler extends AnvilEventHandler<AnvilCraftPreEvent>
{
    protected ItemStack leftItemStack;
    protected ItemStack rightItemStack;
    protected AnvilCraftEventWrapper event;

    @Override
    public boolean tryHandle(AnvilCraftPreEvent event)
    {
        this.event = new AnvilCraftEventWrapper(event);
        rightItemStack = event.getRight();
        leftItemStack = event.getLeft();

        return super.tryHandle(event);
    }
}
