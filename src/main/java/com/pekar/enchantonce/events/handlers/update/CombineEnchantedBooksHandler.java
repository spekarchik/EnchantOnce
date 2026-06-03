package com.pekar.enchantonce.events.handlers.update;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.AnvilMergeMode;
import com.pekar.enchantonce.events.handlers.base.AnvilUpdateEventHandler;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import static com.pekar.enchantonce.events.handlers.AnvilHelper.getXpCost;
import static com.pekar.enchantonce.events.handlers.AnvilHelper.setHistoryWeightToResult;

public class CombineEnchantedBooksHandler extends AnvilUpdateEventHandler
{
    @Override
    protected boolean handleInternally()
    {
        if (!Config.PREVENT_INCREASE_ENCHANTMENT_LEVEL.get()) return false;

        if (leftItemStack.is(Items.ENCHANTED_BOOK) && rightItemStack.is(Items.ENCHANTED_BOOK))
        {
            combineEnchantedBooks();
            return true;
        }

        return false;
    }

    private void combineEnchantedBooks()
    {
        var leftEnchs = EnchantmentHelper.getEnchantments(leftItemStack);
        var rightEnchs = EnchantmentHelper.getEnchantments(rightItemStack);

        boolean changed = false;

        // Merge enchantments but do not increase any enchantment level beyond the highest level present in inputs.
        // Merge into the left enchantment collection in-place. We cast to java.util.Map when
        // using get/put to avoid trying to construct incompatible java.util collections from
        // Minecraft/fastutil types.
        for (var entry : rightEnchs.entrySet())
        {
            var key = entry.getKey();

            boolean areEnchantmentsCompatible = EnchantmentHelper.isEnchantmentCompatible(leftEnchs.keySet(), key);
            boolean areEnchantmentsAlreadyPresent = leftEnchs.keySet().contains(key);
            boolean canEnchant = areEnchantmentsCompatible || areEnchantmentsAlreadyPresent;

            if (!canEnchant) continue;

            int rightLevel = entry.getValue();
            int leftLevel = leftEnchs.get(key);
            int finalLevel = Math.max(leftLevel, rightLevel);

            leftEnchs.put(key, finalLevel);
            if (finalLevel != leftLevel) changed = true;
        }

        if (!changed)
        {
            event.cancel();
            return;
        }

        var result = leftItemStack.copy();

        EnchantmentHelper.setEnchantments(leftEnchs, result);
        setHistoryWeightToResult(leftItemStack, rightItemStack, result, true);
        int xpCost = getXpCost(leftItemStack, rightItemStack, AnvilMergeMode.BOOK_BOOK, e -> true);
        event.setOutput(result);
        event.setXpCost(xpCost);
        event.setMaterialCost(1);
    }
}
