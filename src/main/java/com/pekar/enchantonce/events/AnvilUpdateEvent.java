package com.pekar.enchantonce.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AnvilUpdateEvent implements Event
{
    private final Player player;
    private final ItemStack left;
    private final ItemStack right;
    private boolean cancelled;
    private int materialCost = 1;
    private int xpCost;
    private ItemStack output;

    public AnvilUpdateEvent(Player player, ItemStack left, ItemStack right)
    {
        this.player = player;
        this.left = left;
        this.right = right;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setOutput(ItemStack output)
    {
        this.output = output;
    }

    public void setXpCost(int xpCost)
    {
        this.xpCost = xpCost;
    }

    public int getXpCost()
    {
        return xpCost;
    }

    public void setMaterialCost(int materialCost)
    {
        this.materialCost = materialCost;
    }

    public int getMaterialCost()
    {
        return materialCost;
    }

    public void setCanceled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    public boolean isCancelled()
    {
        return cancelled;
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

    public boolean handled()
    {
        return cancelled || (output != null && !output.isEmpty());
    }
}
