package com.pekar.enchantonce.items;

import com.pekar.enchantonce.tab.ModTab;
import net.minecraft.world.item.Item;

public class ModItem extends Item
{
    public ModItem()
    {
        super(new Properties().tab(ModTab.MOD_TAB));
    }
}
