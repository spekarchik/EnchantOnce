package com.pekar.enchantonce.events.handlers.repair;

import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.world.item.Items;

public class ShearsRepairHandler extends GearRepairEventHandler
{
    private static final int SHEARS_REPAIR_PORTIONS = 1;
    private static final int SHEARS_REPAIR_AMOUNT = getRepairAmount(237, SHEARS_REPAIR_PORTIONS);

    @Override
    protected boolean handleInternally()
    {
        if (leftItemStack.is(Items.SHEARS) && rightItemStack.is(Items.IRON_INGOT))
        {
            validateAndRepairCustom(SHEARS_REPAIR_AMOUNT);
            return true;
        }

        return false;
    }
}
