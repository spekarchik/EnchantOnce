package com.pekar.enchantonce.events.handlers.base;

import net.minecraft.world.item.ItemStack;

public abstract class GearRepairEventHandler extends AnvilUpdateEventHandler
{
    private static final int EQUIPMENT_REPAIR_PORTIONS = 4;
    private static final int REPAIR_COST = 2;
    protected static final int TOOL_REPAIR_PORTIONS = 4;

    protected boolean validateAndRepair()
    {
        if (!isValidRepairItem(leftItemStack, rightItemStack) || leftItemStack.getDamageValue() == 0)
            return false;

        int repairAmount = getRepairAmount(leftItemStack.getMaxDamage(), EQUIPMENT_REPAIR_PORTIONS);
        setAndRepair(repairAmount);
        return true;
    }

    protected void validateAndRepairCustom(int repairAmountPerRepairItem)
    {
        if (leftItemStack.getDamageValue() == 0) return;
        setAndRepair(repairAmountPerRepairItem);
    }

    private void setAndRepair(int repairAmountPerRepairItem)
    {
        int materialAmountAvailable = rightItemStack.getCount();
        int materialNumberConsumed = calculateMaterialNumberConsumed(leftItemStack, repairAmountPerRepairItem, materialAmountAvailable);

        if (materialNumberConsumed <= 0) return;

        var result = leftItemStack.copy();
        repair(result, repairAmountPerRepairItem * materialNumberConsumed);
        event.setOutput(result);
        event.setXpCost(REPAIR_COST * materialNumberConsumed);
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

    private static boolean isValidRepairItem(ItemStack itemToRepair, ItemStack repairItem)
    {
        // found in ItemStack (1.21.4)
        return itemToRepair.isValidRepairItem(repairItem);
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
