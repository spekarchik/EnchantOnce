package com.pekar.enchantonce.events.handlers;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.AnvilUpdateEventHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import static com.pekar.enchantonce.events.handlers.AnvilHelper.addLockMarkerIfContainsWindBurst;
import static com.pekar.enchantonce.events.handlers.AnvilHelper.setHistoryWeightToResult;

public class CopyEnchantedBookHandler extends AnvilUpdateEventHandler
{
    @Override
    protected boolean handleInternally()
    {
        if (Config.ALLOW_BOOK_COPYING.isFalse()) return false;

        if (leftItemStack.is(Items.ENCHANTED_BOOK) && rightItemStack.is(Items.BOOK))
        {
            int rightItemStackCount = rightItemStack.getCount();
            int booksToCopyAmount = Math.min(rightItemStackCount, Config.MAX_BOOK_COPIES.getAsInt());

            copyEnchantedBook(booksToCopyAmount);
            return true;
        }

        return false;
    }

    private void copyEnchantedBook(int booksToCopyAmount)
    {
        var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);

        var resultEnchantments = addLockMarkerIfContainsWindBurst(enchantments, event.getLevel());
        ItemStack output = leftItemStack.copy();
        EnchantmentHelper.setEnchantments(output, resultEnchantments);
        setHistoryWeightToResult(leftItemStack, ItemStack.EMPTY, output, false);
        output.setCount(booksToCopyAmount + 1);
        event.setOutput(output);
        event.setMaterialCost(booksToCopyAmount);

        // see GrindstoneMenu.ctor().getExperienceFromItem()
        int cost = 0;
        for (var ench : enchantments.entrySet())
        {
            var key = ench.getKey().value();
            var value = ench.getIntValue();
            if (!ench.getKey().is(PERSISTENT))
            {
                cost += key.getMinCost(value) / 17;
            }
        }

        if (cost < 1) cost = 1;
        event.setXpCost(booksToCopyAmount * cost);
    }
}
