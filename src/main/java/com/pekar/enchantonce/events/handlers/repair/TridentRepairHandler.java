package com.pekar.enchantonce.events.handlers.repair;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.world.item.Items;

public class TridentRepairHandler extends GearRepairEventHandler
{
    private static final int TRIDENT_REPAIR_AMOUNT = getRepairAmount(249, TOOL_REPAIR_PORTIONS);

    @Override
    protected boolean handleInternally()
    {
        if (Config.ALLOW_NONSTANDARD_REPAIRS.isFalse()) return false;

        if (leftItemStack.is(Items.TRIDENT) && rightItemStack.is(Items.PRISMARINE_SHARD))
        {
            validateAndRepairCustom(TRIDENT_REPAIR_AMOUNT);
            return true;
        }

        return false;
    }
}
