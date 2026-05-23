package com.pekar.enchantonce.events.handlers.update.repair;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.world.item.Items;

public class ShearsRepairHandler extends GearRepairEventHandler
{
    private static final int SHEARS_REPAIR_PORTIONS = 1;

    @Override
    protected boolean handleInternally()
    {
        if (Config.ALLOW_NONSTANDARD_REPAIRS.isFalse()) return false;

        int repairAmount = getRepairAmount(leftItemStack.getMaxDamage(), SHEARS_REPAIR_PORTIONS);

        if (leftItemStack.is(Items.SHEARS) && rightItemStack.is(Items.IRON_INGOT))
        {
            validateAndRepairCustom(repairAmount);
            return true;
        }

        return false;
    }
}
