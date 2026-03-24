package com.pekar.enchantonce.events.handlers.repair;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

public class ShieldRepairHandler extends GearRepairEventHandler
{
    private static final int SHIELD_REPAIR_PORTIONS = 2;
    private static final int SHIELD_REPAIR_AMOUNT = getRepairAmount(335, SHIELD_REPAIR_PORTIONS);

    @Override
    protected boolean handleInternally()
    {
        if (Config.ALLOW_NONSTANDARD_REPAIRS.isFalse()) return false;

        if (rightItemStack.is(ItemTags.PLANKS) && leftItemStack.is(Items.SHIELD))
        {
            validateAndRepairCustom(SHIELD_REPAIR_AMOUNT);
            return true;
        }

        return false;
    }
}
