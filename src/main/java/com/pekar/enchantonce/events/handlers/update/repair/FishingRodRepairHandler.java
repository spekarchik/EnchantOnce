package com.pekar.enchantonce.events.handlers.update.repair;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;
import net.minecraft.world.item.Items;

public class FishingRodRepairHandler extends GearRepairEventHandler
{
    @Override
    protected boolean handleInternally()
    {
        if (!Config.ALLOW_NONSTANDARD_REPAIRS.get()) return false;

        int repairAmount = getRepairAmount(leftItemStack.getMaxDamage(), TOOL_REPAIR_PORTIONS);

        boolean isKindOfFishingRod = leftItemStack.is(Items.FISHING_ROD)
                || leftItemStack.is(Items.CARROT_ON_A_STICK)
                || leftItemStack.is(Items.WARPED_FUNGUS_ON_A_STICK);

        if (isKindOfFishingRod && rightItemStack.is(Items.STRING))
        {
            validateAndRepairCustom(repairAmount);
            return true;
        }

        return false;
    }
}
