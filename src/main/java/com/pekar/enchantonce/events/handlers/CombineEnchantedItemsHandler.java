package com.pekar.enchantonce.events.handlers;

import com.pekar.enchantonce.enchantments.EnchantmentRegistry;
import com.pekar.enchantonce.events.handlers.base.AnvilUpdateEventHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import static com.pekar.enchantonce.events.handlers.AnvilHelper.getXpCost;
import static com.pekar.enchantonce.events.handlers.AnvilHelper.setHistoryWeightToResult;

public class CombineEnchantedItemsHandler extends AnvilUpdateEventHandler
{
    @Override
    protected boolean handleInternally()
    {
        if (!leftItemStack.is(Items.ENCHANTED_BOOK) && leftItemStack.isEnchanted() && (rightItemStack.isEnchanted() || rightItemStack.is(Items.ENCHANTED_BOOK)))
        {
            boolean areItemsTheSame = leftItemStack.getItem() == rightItemStack.getItem();

            if (areItemsTheSame || rightItemStack.is(Items.ENCHANTED_BOOK))
            {
                combineEnchantedItems();
                return true;
            }
        }

        return false;
    }

    private void combineEnchantedItems()
    {
        var leftEnchs = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
        var rightEnchs = EnchantmentHelper.getEnchantmentsForCrafting(rightItemStack);

        var leftEnchMutable = new ItemEnchantments.Mutable(leftEnchs);
        boolean changed = false;

        for (var entry : rightEnchs.entrySet())
        {
            var key = entry.getKey();
            boolean isEnchantmentSupportedByItem = leftItemStack.supportsEnchantment(key);
            boolean areEnchantmentsCompatible = EnchantmentHelper.isEnchantmentCompatible(leftEnchs.keySet(), key);
            boolean areEnchantmentsAlreadyPresent = leftEnchs.keySet().contains(key);
            boolean canEnchant = isEnchantmentSupportedByItem && (areEnchantmentsCompatible || areEnchantmentsAlreadyPresent);

            if (!canEnchant) continue;

            boolean isWindBurst = key.is(Enchantments.WIND_BURST);
            int rightLevel = entry.getIntValue();
            int leftLevel = leftEnchMutable.getLevel(key);
            int finalLevel;

            if (isWindBurst && rightLevel == leftLevel && rightItemStack.is(Items.ENCHANTED_BOOK)
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

        var result = leftItemStack.copy();
        int resultDamageValue = getResultDamageValue(leftItemStack, rightItemStack);

        boolean durabilityChanged = leftItemStack.isDamageableItem() && rightItemStack.isDamageableItem()
                && (leftItemStack.getDamageValue() != resultDamageValue || rightItemStack.getDamageValue() != resultDamageValue);

        if (!changed && !durabilityChanged)
        {
            event.cancel();
            return;
        }

        result.setDamageValue(resultDamageValue);
        EnchantmentHelper.setEnchantments(result, leftEnchMutable.toImmutable());
        setHistoryWeightToResult(leftItemStack, rightItemStack, result, true);
        var anvilMergeMode = rightItemStack.is(Items.ENCHANTED_BOOK)? AnvilMergeMode.ITEM_BOOK : AnvilMergeMode.ITEM_ITEM;
        int xpCost = getXpCost(leftItemStack, rightItemStack, anvilMergeMode, leftItemStack::supportsEnchantment);
        event.setOutput(result);
        event.setXpCost(xpCost);
        event.setMaterialCost(1);
    }

    private static int getResultDamageValue(ItemStack left, ItemStack right)
    {
        if (!left.isDamageableItem())
        {
            return left.getDamageValue();
        }

        if (!right.isDamageableItem() || !left.is(right.getItem()))
        {
            return left.getDamageValue();
        }

        int leftMax = left.getMaxDamage();
        int leftRemaining = leftMax - left.getDamageValue();
        int rightRemaining = right.getMaxDamage() - right.getDamageValue();

        int combinedRemaining = leftRemaining + rightRemaining + leftMax * 12 / 100;

        int resultDamage = leftMax - combinedRemaining;

        if (resultDamage < 0)
        {
            resultDamage = 0;
        }

        return resultDamage;
    }
}
