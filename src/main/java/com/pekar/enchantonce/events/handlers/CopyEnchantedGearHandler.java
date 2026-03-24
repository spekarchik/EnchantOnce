package com.pekar.enchantonce.events.handlers;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.AnvilUpdateEventHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import static com.pekar.enchantonce.events.handlers.AnvilHelper.setHistoryWeightToResult;

public class CopyEnchantedGearHandler extends AnvilUpdateEventHandler
{
    @Override
    protected boolean handleInternally()
    {
        if (Config.ALLOW_GEAR_COPYING.isFalse()) return false;

        if (leftItemStack.isDamageableItem() && leftItemStack.getDamageValue() == 0 && leftItemStack.isEnchanted() &&
                rightItemStack.isEnchantable() && !rightItemStack.isEnchanted() && rightItemStack.getDamageValue() == 0)
        {
            boolean areItemsTheSame = rightItemStack.getItem() == leftItemStack.getItem();

            if (areItemsTheSame)
            {
                copyEnchantedGear();
                return true;
            }
        }

        return false;
    }

    private void copyEnchantedGear()
    {
        var result = leftItemStack.copy();
        var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
        EnchantmentHelper.setEnchantments(result, enchantments);
        setHistoryWeightToResult(leftItemStack, ItemStack.EMPTY, result, false);
        result.setCount(2);
        event.setOutput(result);
        event.setXpCost(Config.GEAR_COPYING_COST.getAsInt());
    }
}
