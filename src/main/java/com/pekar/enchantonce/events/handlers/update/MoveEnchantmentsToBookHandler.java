package com.pekar.enchantonce.events.handlers.update;

import com.pekar.enchantonce.Config;
import com.pekar.enchantonce.events.handlers.base.AnvilUpdateEventHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class MoveEnchantmentsToBookHandler extends AnvilUpdateEventHandler
{
    @Override
    protected boolean handleInternally()
    {
        if (Config.ALLOW_MOVE_ENCHANTMENTS_TO_BOOK.isFalse()) return false;

        if (leftItemStack.isDamageableItem() && rightItemStack.is(Items.BOOK))
        {
            if (!leftItemStack.isEnchanted() || leftItemStack.isDamaged() || rightItemStack.isEnchanted())
                return false;

            moveEnchantmentsToBook();
            return true;
        }

        return false;
    }

    private void moveEnchantmentsToBook()
    {
        var result = new ItemStack(Items.ENCHANTED_BOOK);
        var enchantments = EnchantmentHelper.getEnchantments(leftItemStack);
        EnchantmentHelper.setEnchantments(enchantments, result);
        // not to copy history weight to the book
        event.setOutput(result);
        event.setXpCost(Config.MOVE_ENCHANTMENTS_TO_BOOK_COST.getAsInt());
        event.setMaterialCost(1);
    }
}
