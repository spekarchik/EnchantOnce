package com.pekar.enchantonce.events.handlers;

import com.pekar.enchantonce.events.handlers.base.AnvilUpdateEventHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import static com.pekar.enchantonce.events.handlers.AnvilHelper.addLockMarkerIfContainsWindBurst;

public class MoveEnchantmentsToBookHandler extends AnvilUpdateEventHandler
{
    private static final int COPY_ENCHANTS_TO_BOOK_COST = 1;

    @Override
    protected boolean handleInternally()
    {
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
        var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
        var resultEnchantments = addLockMarkerIfContainsWindBurst(enchantments, event.getLevel());
        EnchantmentHelper.setEnchantments(result, resultEnchantments);
        // not to copy history weight to the book
        event.setOutput(result);
        event.setXpCost(COPY_ENCHANTS_TO_BOOK_COST);
        event.setMaterialCost(1);
    }
}
