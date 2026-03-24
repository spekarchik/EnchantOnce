package com.pekar.enchantonce.events.handlers;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.enchantments.EnchantmentRegistry;
import com.pekar.enchantonce.events.handlers.base.AnvilUpdateEventHandler;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

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
        var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
        var resultEnchantments = new ItemEnchantments.Mutable(enchantments);
        boolean changed = false;
        int flintsAvailable = rightItemStack.getCount();
        int maxLevel = 0;
        boolean hasCurses = false;

        for (var entry : enchantments.entrySet())
        {
            var key = entry.getKey();
            int level = entry.getIntValue();
            maxLevel = Math.max(maxLevel, level);
            if (key.is(EnchantmentTags.CURSE)) hasCurses = true;
        }

        int levelsToRemove = hasCurses ? maxLevel : (maxLevel - 1); // we can remove all enchantments and keep only curses
        int flintsConsumed = Math.min(levelsToRemove, flintsAvailable);

        // modify the existing enchantment collection in-place: lower each level by 1, remove if becomes 0
        for (var entry : enchantments.entrySet())
        {
            var key = entry.getKey();

            // Do not touch curses: keep them intact
            if (key.is(PERSISTENT)) continue;

            int level = entry.getIntValue();

            int newLevel = Math.max(0, level - flintsConsumed);
            resultEnchantments.set(key, newLevel);
            changed = true;
        }

        if (resultEnchantments.keySet().stream().noneMatch(x -> x.is(Enchantments.WIND_BURST))
                && resultEnchantments.keySet().stream().anyMatch(x -> x.is(EnchantmentRegistry.LOCK_MARKER)))
        {
            var enchantmentRegistry = AnvilHelper.getEnchantmentRegistry(event.getLevel());
            resultEnchantments.set(enchantmentRegistry.getHolderOrThrow(EnchantmentRegistry.LOCK_MARKER), 0);
        }

        if (!changed || flintsConsumed == 0 || resultEnchantments.keySet().isEmpty())
        {
            event.cancel();
            return;
        }

        var result = leftItemStack.copy();
        EnchantmentHelper.setEnchantments(result, resultEnchantments.toImmutable());
        AnvilHelper.setHistoryWeightToResult(leftItemStack, rightItemStack, result, false);
        event.setOutput(result);
        event.setMaterialCost(flintsConsumed);
        event.setXpCost(flintsConsumed * Config.DECREASE_ENCHANTMENT_LEVEL_COST.getAsInt());
    }
}
