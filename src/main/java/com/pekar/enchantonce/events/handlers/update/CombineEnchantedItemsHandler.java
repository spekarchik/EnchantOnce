package com.pekar.enchantonce.events.handlers.update;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.AnvilMergeMode;
import com.pekar.enchantonce.events.handlers.base.AnvilUpdateEventHandler;
import com.pekar.enchantonce.utils.ItemStackWrapper;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.HashMap;

import static com.pekar.enchantonce.events.handlers.AnvilHelper.getXpCost;
import static com.pekar.enchantonce.events.handlers.AnvilHelper.setHistoryWeightToResult;

public class CombineEnchantedItemsHandler extends AnvilUpdateEventHandler
{
    @Override
    protected boolean handleInternally()
    {
        if (Config.PREVENT_INCREASE_ENCHANTMENT_LEVEL.isFalse()) return false;

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
        var leftEnchs = EnchantmentHelper.getEnchantments(leftItemStack);
        var rightEnchs = EnchantmentHelper.getEnchantments(rightItemStack);
        var resultEnchantments = new HashMap<>(leftEnchs);

        boolean changed = false;

        for (var entry : rightEnchs.entrySet())
        {
            var key = entry.getKey();
            boolean isEnchantmentSupportedByItem = ItemStackWrapper.of(leftItemStack).supportsEnchantment(Holder.direct(key));
            boolean areEnchantmentsCompatible = EnchantmentHelper.isEnchantmentCompatible(resultEnchantments.keySet(), key);
            boolean areEnchantmentsAlreadyPresent = resultEnchantments.containsKey(key);
            boolean canEnchant = isEnchantmentSupportedByItem && (areEnchantmentsCompatible || areEnchantmentsAlreadyPresent);

            if (!canEnchant) continue;

            int rightLevel = entry.getValue();
            int leftLevel = resultEnchantments.getOrDefault(key, 0);
            int finalLevel = Math.max(leftLevel, rightLevel);

            if (finalLevel == 0)
                resultEnchantments.remove(key);
            else
                resultEnchantments.put(key, finalLevel);

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
        EnchantmentHelper.setEnchantments(resultEnchantments, result);
        setHistoryWeightToResult(leftItemStack, rightItemStack, result, true);
        var anvilMergeMode = rightItemStack.is(Items.ENCHANTED_BOOK)? AnvilMergeMode.ITEM_BOOK : AnvilMergeMode.ITEM_ITEM;
        int xpCost = getXpCost(leftItemStack, rightItemStack, anvilMergeMode, ench -> ItemStackWrapper.of(leftItemStack).supportsEnchantment(Holder.direct(ench)));
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
