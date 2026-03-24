package com.pekar.enchantonce.events.handlers;

import com.pekar.enchantonce.enchantments.EnchantmentRegistry;
import com.pekar.enchantonce.events.handlers.base.AnvilUpdateEventHandler;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import static com.pekar.enchantonce.events.handlers.AnvilHelper.getXpCost;
import static com.pekar.enchantonce.events.handlers.AnvilHelper.setHistoryWeightToResult;

public class CombineEnchantedBooksHandler extends AnvilUpdateEventHandler
{
    @Override
    protected boolean handleInternally()
    {
        if (leftItemStack.is(Items.ENCHANTED_BOOK) && rightItemStack.is(Items.ENCHANTED_BOOK))
        {
            combineEnchantedBooks();
            return true;
        }

        return false;
    }

    private void combineEnchantedBooks()
    {
        var leftEnchs = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
        var rightEnchs = EnchantmentHelper.getEnchantmentsForCrafting(rightItemStack);

        var leftEnchMutable = new ItemEnchantments.Mutable(leftEnchs);
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

            boolean isWindBurst = key.is(Enchantments.WIND_BURST);
            int rightLevel = entry.getIntValue();
            int leftLevel = leftEnchMutable.getLevel(key);
            int finalLevel;

            if (isWindBurst && rightLevel == leftLevel
                    && rightEnchs.keySet().stream().noneMatch(x -> x.is(EnchantmentRegistry.LOCK_MARKER)))
            {
                finalLevel = Math.min(rightLevel + 1, key.value().getMaxLevel());
            }
            else
            {
                finalLevel = Math.max(leftLevel, rightLevel);
            }

            leftEnchMutable.set(key, finalLevel);
            if (finalLevel != leftLevel) changed = true;
        }

        if (!changed)
        {
            event.cancel();
            return;
        }

        var result = leftItemStack.copy();

        EnchantmentHelper.setEnchantments(result, leftEnchMutable.toImmutable());
        setHistoryWeightToResult(leftItemStack, rightItemStack, result, true);
        int xpCost = getXpCost(leftItemStack, rightItemStack, AnvilMergeMode.BOOK_BOOK, e -> true);
        event.setOutput(result);
        event.setXpCost(xpCost);
        event.setMaterialCost(1);
    }
}
