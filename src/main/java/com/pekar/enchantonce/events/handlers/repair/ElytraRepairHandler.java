package com.pekar.enchantonce.events.handlers.repair;

import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.world.item.Items;

public class ElytraRepairHandler extends GearRepairEventHandler
{
    private static final int ELYTRA_REPAIR_PORTIONS = 1;
    private static final int ELYTRA_REPAIR_AMOUNT = getRepairAmount(431, ELYTRA_REPAIR_PORTIONS);

    @Override
    protected boolean handleInternally()
    {
        if (rightItemStack.is(Items.PHANTOM_MEMBRANE) && leftItemStack.is(Items.ELYTRA))
        {
            validateAndRepairCustom(ELYTRA_REPAIR_AMOUNT);
            return true;
        }

        return false;
    }
}
