package com.pekar.enchantonce.events.handlers.craft;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.AnvilHelper;
import com.pekar.enchantonce.events.handlers.base.AnvilCraftEventHandler;
import net.minecraft.world.item.Items;

public class KeepItemAfterMovingEnchsToBookHandler extends AnvilCraftEventHandler
{
    @Override
    protected boolean handleInternally()
    {
        if (!Config.ALLOW_MOVE_ENCHANTMENTS_TO_BOOK.get() || !Config.KEEP_ITEM_WHEN_MOVING_ENCHANTMENTS_TO_BOOK.get()) return false;

        if (leftItemStack.isDamageableItem() && rightItemStack.is(Items.BOOK))
        {
            if (!leftItemStack.isEnchanted() || leftItemStack.isDamaged() || rightItemStack.isEnchanted())
                return false;

            returnItemIntoInventory();
            return true;
        }

        return false;
    }

    private void returnItemIntoInventory()
    {
        var player = event.getPlayer();
        if (player == null) return;

        var itemStack = leftItemStack.copy();
        AnvilHelper.cleanEnchantmentsExceptCurses(itemStack);
        player.getInventory().placeItemBackInInventory(itemStack);
    }
}
