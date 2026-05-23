package com.pekar.enchantonce.events.handlers.base;

import com.pekar.enchantonce.events.handlers.craft.AnvilCraftEventWrapper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.AnvilCraftEvent;

public abstract class AnvilCraftEventHandler extends AnvilEventHandler<AnvilCraftEvent>
{
    protected ItemStack leftItemStack;
    protected ItemStack rightItemStack;
    protected AnvilCraftEventWrapper event;

    @Override
    public boolean tryHandle(AnvilCraftEvent event)
    {
        this.event = new AnvilCraftEventWrapper(event);
        rightItemStack = event.getRight();
        leftItemStack = event.getLeft();

        return super.tryHandle(event);
    }
}
