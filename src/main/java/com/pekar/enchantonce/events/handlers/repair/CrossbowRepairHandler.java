package com.pekar.enchantonce.events.handlers.repair;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.world.item.Items;

public class CrossbowRepairHandler extends GearRepairEventHandler
{
    private static final int CROSSBOW_REPAIR_AMOUNT = getRepairAmount(464, TOOL_REPAIR_PORTIONS);

    @Override
    protected boolean handleInternally()
    {
        if (Config.ALLOW_NONSTANDARD_REPAIRS.isFalse()) return false;

        if (leftItemStack.is(Items.CROSSBOW) && rightItemStack.is(Items.STRING))
        {
            validateAndRepairCustom(CROSSBOW_REPAIR_AMOUNT);
            return true;
        }

        return false;
    }
}
