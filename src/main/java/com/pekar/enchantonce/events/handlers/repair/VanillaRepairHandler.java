package com.pekar.enchantonce.events.handlers.repair;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.world.item.ItemStack;

public class VanillaRepairHandler extends GearRepairEventHandler
{
    private static final int EQUIPMENT_REPAIR_PORTIONS = 4;

    @Override
    protected boolean handleInternally()
    {
        if (Config.ALLOW_FIXED_REPAIR_COST.isFalse()) return false;

        return leftItemStack.isDamageableItem() && validateAndRepair();
    }

    protected boolean validateAndRepair()
    {
        if (!isValidRepairItem(leftItemStack, rightItemStack) || leftItemStack.getDamageValue() == 0)
            return false;

        int repairAmount = getRepairAmount(leftItemStack.getMaxDamage(), EQUIPMENT_REPAIR_PORTIONS);
        setAndRepair(repairAmount);
        return true;
    }

    private static boolean isValidRepairItem(ItemStack itemToRepair, ItemStack repairItem)
    {
        // found in ItemStack (1.21.4)
        return itemToRepair.isValidRepairItem(repairItem);
    }
}
