package com.pekar.enchantonce.events.handlers;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.AnvilUpdateEventHandler;
import net.minecraft.world.item.Items;

import static com.pekar.enchantonce.events.handlers.AnvilHelper.getXpCost;

public class EnchantGearWithBookHandler extends AnvilUpdateEventHandler
{
    @Override
    protected boolean handleInternally()
    {
        if (Config.PREVENT_INCREASE_ENCHANTMENT_LEVEL.isFalse()) return false;

        if (leftItemStack.isEnchantable() && !leftItemStack.is(Items.ENCHANTED_BOOK) && !leftItemStack.isEnchanted()
                && rightItemStack.is(Items.ENCHANTED_BOOK))
        {
            enchantGearWithBook();
            return true;
        }

        return false;
    }

    private void enchantGearWithBook()
    {
        int xpCost = getXpCost(leftItemStack, rightItemStack, AnvilMergeMode.ITEM_BOOK, leftItemStack::supportsEnchantment);
        event.setXpCost(xpCost);
    }
}
