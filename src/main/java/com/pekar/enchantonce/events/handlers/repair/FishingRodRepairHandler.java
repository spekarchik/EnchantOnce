package com.pekar.enchantonce.events.handlers.repair;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.world.item.Items;

public class FishingRodRepairHandler extends GearRepairEventHandler
{
    private static final int FISHING_ROD_REPAIR_AMOUNT = getRepairAmount(63, TOOL_REPAIR_PORTIONS);

    @Override
    protected boolean handleInternally()
    {
        if (Config.ALLOW_NONSTANDARD_REPAIRS.isFalse()) return false;

        if (leftItemStack.is(Items.FISHING_ROD) && rightItemStack.is(Items.STRING))
        {
            validateAndRepairCustom(FISHING_ROD_REPAIR_AMOUNT);
            return true;
        }

        return false;
    }
}
