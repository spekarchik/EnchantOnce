package com.pekar.enchantonce.events.handlers.update.repair;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

public class ShieldRepairHandler extends GearRepairEventHandler
{
    private static final int SHIELD_REPAIR_PORTIONS = 2;

    @Override
    protected boolean handleInternally()
    {
        if (!Config.ALLOW_NONSTANDARD_REPAIRS.get()) return false;

        int repairAmount = getRepairAmount(leftItemStack.getMaxDamage(), SHIELD_REPAIR_PORTIONS);

        if (rightItemStack.is(ItemTags.PLANKS) && leftItemStack.is(Items.SHIELD))
        {
            validateAndRepairCustom(repairAmount);
            return true;
        }

        return false;
    }
}
