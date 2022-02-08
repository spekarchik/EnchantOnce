package com.pekar.enchantonce.tab;

import com.pekar.enchantonce.Main;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModTab extends CreativeModeTab
{
    public static CreativeModeTab MOD_TAB = new ModTab(Main.MODID);

    public ModTab(String tabName)
    {
        super(tabName);
    }

    @Override
    public ItemStack makeIcon()
    {
        return new ItemStack(Items.ENCHANTED_BOOK);
    }
}
