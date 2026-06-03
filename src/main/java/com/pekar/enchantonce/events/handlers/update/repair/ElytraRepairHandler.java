package com.pekar.enchantonce.events.handlers.update.repair;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.world.item.Items;

public class ElytraRepairHandler extends GearRepairEventHandler
{
    private static final int ELYTRA_REPAIR_PORTIONS = 1;

    @Override
    protected boolean handleInternally()
    {
        if (!Config.ALLOW_NONSTANDARD_REPAIRS.get()) return false;

        int repairAmount = getRepairAmount(leftItemStack.getMaxDamage(), ELYTRA_REPAIR_PORTIONS);

        if (rightItemStack.is(Items.PHANTOM_MEMBRANE) && leftItemStack.is(Items.ELYTRA))
        {
            validateAndRepairCustom(repairAmount);
            return true;
        }

        return false;
    }
}
