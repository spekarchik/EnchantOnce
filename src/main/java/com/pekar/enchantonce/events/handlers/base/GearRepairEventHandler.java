package com.pekar.enchantonce.events.handlers.base;

import com.pekar.enchantonce.Config;
import net.minecraft.world.item.ItemStack;

public abstract class GearRepairEventHandler extends AnvilUpdateEventHandler
{
    protected static final int TOOL_REPAIR_PORTIONS = 4;

    protected void validateAndRepairCustom(int repairAmountPerRepairItem)
    {
        if (leftItemStack.getDamageValue() == 0) return;
        setAndRepair(repairAmountPerRepairItem);
    }

    protected void setAndRepair(int repairAmountPerRepairItem)
    {
        int materialAmountAvailable = rightItemStack.getCount();
        int materialNumberConsumed = calculateMaterialNumberConsumed(leftItemStack, repairAmountPerRepairItem, materialAmountAvailable);

        if (materialNumberConsumed <= 0) return;

        var result = leftItemStack.copy();
        repair(result, repairAmountPerRepairItem * materialNumberConsumed);
        event.setOutput(result);
        event.setXpCost(Config.FIXED_REPAIR_COST.getAsInt() * materialNumberConsumed);
        event.setMaterialCost(materialNumberConsumed);
    }

    protected static int getRepairAmount(int maxDamage, int portions)
    {
        return (maxDamage + portions - 1) / portions;
    }

    private static void repair(ItemStack itemToRepair, int damageDecrement)
    {
        int newDamage = itemToRepair.getDamageValue() - damageDecrement;
        itemToRepair.setDamageValue(Math.max(newDamage, 0));
    }

    private static int calculateMaterialNumberConsumed(ItemStack itemToRepair, int repairAmountPerRepairingItem, int materialNumberAvailable)
    {
        if (repairAmountPerRepairingItem <= 0 || materialNumberAvailable <= 0)
            return 0;

        int currentDamage = itemToRepair.getDamageValue();
        if (currentDamage <= 0)
            return 0;

        int needed = (currentDamage + repairAmountPerRepairingItem - 1) / repairAmountPerRepairingItem;

        return Math.min(needed, materialNumberAvailable);
    }
}
