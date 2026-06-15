package com.pekar.enchantonce.mixin;

import com.pekar.enchantonce.Config;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin
{
    private static final int VANILLA_TOO_EXPENSIVE_LIMIT = 40;
    private static final int DISABLED_TOO_EXPENSIVE_LIMIT = Integer.MAX_VALUE;

    @ModifyConstant(method = "createResultInternal", constant = @Constant(intValue = VANILLA_TOO_EXPENSIVE_LIMIT, ordinal = 1))
    private int allowRenameOnlyCostsAboveVanillaLimit(int value)
    {
        return getTooExpensiveLimit(value);
    }

    @ModifyConstant(method = "createResultInternal", constant = @Constant(intValue = VANILLA_TOO_EXPENSIVE_LIMIT, ordinal = 2))
    private int allowResultCostsAboveVanillaLimit(int value)
    {
        return getTooExpensiveLimit(value);
    }

    private int getTooExpensiveLimit(int vanillaLimit)
    {
        return Config.ALLOW_HIGH_ANVIL_COST.isTrue() ? DISABLED_TOO_EXPENSIVE_LIMIT : vanillaLimit;
    }
}
