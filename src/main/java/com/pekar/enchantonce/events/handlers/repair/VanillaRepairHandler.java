package com.pekar.enchantonce.events.handlers.repair;

import com.pekar.enchantonce.events.handlers.base.GearRepairEventHandler;

public class VanillaRepairHandler extends GearRepairEventHandler
{
    @Override
    protected boolean handleInternally()
    {
        return leftItemStack.isDamageableItem() && validateAndRepair();
    }
}
