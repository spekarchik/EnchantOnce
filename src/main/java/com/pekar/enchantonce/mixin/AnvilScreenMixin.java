package com.pekar.enchantonce.mixin;

import com.pekar.enchantonce.Config;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin
{
    @ModifyConstant(method = "extractLabels", constant = @Constant(intValue = 40))
    private int allowCostLabelAboveVanillaLimit(int value)
    {
        return Config.ALLOW_HIGH_ANVIL_COST.isTrue() ? Integer.MAX_VALUE : value;
    }
}
