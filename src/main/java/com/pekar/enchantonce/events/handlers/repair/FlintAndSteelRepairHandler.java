package com.pekar.enchantonce.events.handlers.repair;

import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.world.item.Items;

public class FlintAndSteelRepairHandler extends GearRepairEventHandler
{
    private static final int FLINT_AND_STEEL_REPAIR_PORTIONS = 1;
    private static final int FLINT_AND_STEEL_REPAIR_AMOUNT = getRepairAmount(63, FLINT_AND_STEEL_REPAIR_PORTIONS);

    @Override
    protected boolean handleInternally()
    {
        if (leftItemStack.is(Items.FLINT_AND_STEEL) && rightItemStack.is(Items.FLINT))
        {
            validateAndRepairCustom(FLINT_AND_STEEL_REPAIR_AMOUNT);
            return true;
        }

        return false;
    }
}
