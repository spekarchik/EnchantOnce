package com.pekar.enchantonce.events.handlers.update.repair;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.world.item.Items;

public class FlintAndSteelRepairHandler extends GearRepairEventHandler
{
    private static final int FLINT_AND_STEEL_REPAIR_PORTIONS = 1;

    @Override
    protected boolean handleInternally()
    {
        if (!Config.ALLOW_NONSTANDARD_REPAIRS.get()) return false;

        int repairAmount = getRepairAmount(leftItemStack.getMaxDamage(), FLINT_AND_STEEL_REPAIR_PORTIONS);

        if (leftItemStack.is(Items.FLINT_AND_STEEL) && rightItemStack.is(Items.FLINT))
        {
            validateAndRepairCustom(repairAmount);
            return true;
        }

        return false;
    }
}
