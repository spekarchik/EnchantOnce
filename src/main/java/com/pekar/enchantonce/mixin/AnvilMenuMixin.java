package com.pekar.enchantonce.mixin;

import com.pekar.enchantonce.events.AnvilEvents;
import com.pekar.enchantonce.events.AnvilUpdateEvent;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin
{
    @Shadow
    private DataSlot cost;
    @Shadow
    private int repairItemCountCost;

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void onCreateResult(CallbackInfo ci)
    {
        var self = (AnvilMenu)(Object)this;
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
}
