package com.pekar.enchantonce.mixin;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.AnvilCraftPreEvent;
import com.pekar.enchantonce.events.AnvilEvents;
import com.pekar.enchantonce.events.AnvilUpdateEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin
{
    private static final int VANILLA_TOO_EXPENSIVE_LIMIT = 40;
    private static final int DISABLED_TOO_EXPENSIVE_LIMIT = Integer.MAX_VALUE;

    @Shadow
    private DataSlot cost;
    @Shadow
    private int repairItemCountCost;

    @ModifyConstant(method = "createResult", constant = @Constant(intValue = VANILLA_TOO_EXPENSIVE_LIMIT, ordinal = 1))
    private int allowRenameOnlyCostsAboveVanillaLimit(int value)
    {
        return getTooExpensiveLimit(value);
    }

    @ModifyConstant(method = "createResult", constant = @Constant(intValue = VANILLA_TOO_EXPENSIVE_LIMIT, ordinal = 2))
    private int allowResultCostsAboveVanillaLimit(int value)
    {
        return getTooExpensiveLimit(value);
    }

    private int getTooExpensiveLimit(int vanillaLimit)
    {
        return Config.ALLOW_HIGH_ANVIL_COST.isTrue() ? DISABLED_TOO_EXPENSIVE_LIMIT : vanillaLimit;
    }

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void onCreateResult(CallbackInfo ci)
    {
        var accessor = (ItemCombinerMenuAccessor)this;
        var player = accessor.getPlayer();
        var inputSlots = accessor.getInputSlots();
        var resultSlots = accessor.getResultSlots();

        var left = inputSlots.getItem(0);
        var right = inputSlots.getItem(1);
        var event = new AnvilUpdateEvent(player, left, right);
        AnvilEvents.onAnvilUpdateEvent(event);
        if (!event.handled()) return;
        ci.cancel();
        if (event.isCancelled()) return;

        resultSlots.setItem(0, event.getOutput());
        cost.set(event.getXpCost());
        repairItemCountCost = event.getMaterialCost();
    }

    @Inject(method = "onTake", at = @At("HEAD"))
    private void onTake(Player player, ItemStack stack, CallbackInfo ci)
    {
        var accessor = (ItemCombinerMenuAccessor)this;
        var inputSlots = accessor.getInputSlots();
        var resultSlots = accessor.getResultSlots();

        var left = inputSlots.getItem(0);
        var right = inputSlots.getItem(1);
        var output = resultSlots.getItem(0);

        var event = new AnvilCraftPreEvent(player, left, right, output);
        AnvilEvents.onAnvilCraftEvent(event);
    }
}
