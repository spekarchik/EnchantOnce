package com.pekar.enchantonce.utils;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

public class ItemStackWrapper
{
    private final ItemStack itemStack;

    private ItemStackWrapper(ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }

    public static ItemStackWrapper of(ItemStack itemStack)
    {
        return new ItemStackWrapper(itemStack);
    }

    public boolean supportsEnchantment(Holder<Enchantment> enchantmentHolder)
    {
        return itemStack.is(Items.ENCHANTED_BOOK) || enchantmentHolder.value().canEnchant(itemStack);
    }
}
