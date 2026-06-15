package com.pekar.enchantonce.events.handlers.update;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.AnvilHelper;
import com.pekar.enchantonce.events.handlers.base.AnvilUpdateEventHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.HashMap;
import java.util.Map;

public class DecreaseBookEnchantmentsHandler extends AnvilUpdateEventHandler
{
    @Override
    protected boolean handleInternally()
    {
        if (Config.ALLOW_DECREASE_ENCHANTMENT_LEVEL.isFalse()) return false;

        if (leftItemStack.is(Items.ENCHANTED_BOOK) && rightItemStack.is(Items.FLINT))
        {
            decreaseBookEnchantments();
            return true;
        }

        return false;
    }

    private void decreaseBookEnchantments()
    {
        var enchantments = EnchantmentHelper.getEnchantments(leftItemStack);
        var resultEnchantments = new HashMap<>(enchantments);
        boolean changed = false;
        int flintsAvailable = rightItemStack.getCount();
        int maxLevel = 0;
        boolean hasCurses = false;

        for (var entry : enchantments.entrySet())
        {
            var key = entry.getKey();
            int level = entry.getValue();
            maxLevel = Math.max(maxLevel, level);
            if (key.isCurse()) hasCurses = true;
        }

        int levelsToRemove = hasCurses ? maxLevel : (maxLevel - 1); // we can remove all enchantments and keep only curses
        int flintsConsumed = Math.min(levelsToRemove, flintsAvailable);

        // modify the existing enchantment collection in-place: lower each level by 1, remove if becomes 0
        for (var entry : enchantments.entrySet())
        {
            var key = entry.getKey();

            // Do not touch curses: keep them intact
            if (key.isCurse()) continue;

            int level = entry.getValue();

            int newLevel = Math.max(0, level - flintsConsumed);
            if (newLevel > 0)
                resultEnchantments.put(key, newLevel);
            else
                resultEnchantments.remove(key);

            changed = true;
        }

        if (!changed || flintsConsumed == 0 || resultEnchantments.isEmpty())
        {
            event.cancel();
            return;
        }

        var result = new ItemStack(leftItemStack.getItem());
        EnchantmentHelper.setEnchantments(resultEnchantments, result);
        AnvilHelper.setHistoryWeightToResult(leftItemStack, rightItemStack, result, false);
        event.setOutput(result);
        event.setMaterialCost(flintsConsumed);
        event.setXpCost(flintsConsumed * Config.DECREASE_ENCHANTMENT_LEVEL_COST.getAsInt());
    }
}
