package com.pekar.enchantonce.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AnvilCraftPreEvent implements Event
{
    private final Player player;
    private final ItemStack left;
    private final ItemStack right;
    private final ItemStack output;

    public AnvilCraftPreEvent(Player player, ItemStack left, ItemStack right, ItemStack output)
    {
        this.player = player;
        this.left = left;
        this.right = right;
        this.output = output;
    }

    public Player getPlayer()
    {
        return player;
    }

    public ItemStack getLeft()
    {
        return left;
    }

    public ItemStack getRight()
    {
        return right;
    }

    public ItemStack getOutput()
    {
        return output;
    }
}
