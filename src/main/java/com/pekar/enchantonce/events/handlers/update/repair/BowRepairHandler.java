package com.pekar.enchantonce.events.handlers.update.repair;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.world.item.Items;

public class BowRepairHandler extends GearRepairEventHandler
{
    @Override
    protected boolean handleInternally()
    {
        if (Config.ALLOW_NONSTANDARD_REPAIRS.isFalse()) return false;

        int repairAmount = getRepairAmount(leftItemStack.getMaxDamage(), TOOL_REPAIR_PORTIONS);

        if (leftItemStack.is(Items.BOW) && rightItemStack.is(Items.STRING))
        {
            validateAndRepairCustom(repairAmount);
            return true;
        }

        return false;
    }
}
