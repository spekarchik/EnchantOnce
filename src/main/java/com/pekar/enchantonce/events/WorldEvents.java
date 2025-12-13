package com.pekar.enchantonce.events;

import com.mojang.logging.LogUtils;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;

public class WorldEvents implements IEventHandler
{
    private static final int EQUIPMENT_REPAIR_PORTIONS = 4;
    private static final int TOOL_REPAIR_PORTIONS = 4;
    private static final int ELYTRA_REPAIR_PORTIONS = 1;
    private static final int SHIELD_REPAIR_PORTIONS = 2;
    private static final int FLINT_AND_STEEL_REPAIR_PORTIONS = 1;
    private static final int SHEARS_REPAIR_PORTIONS = 1;
    private static final int ELYTRA_REPAIR_AMOUNT = Items.ELYTRA.getDefaultInstance().getMaxDamage()  / ELYTRA_REPAIR_PORTIONS;
    private static final int SHIELD_REPAIR_AMOUNT = Items.SHIELD.getDefaultInstance().getMaxDamage() / SHIELD_REPAIR_PORTIONS;
    private static final int BOW_REPAIR_AMOUNT = Items.BOW.getDefaultInstance().getMaxDamage() / TOOL_REPAIR_PORTIONS;
    private static final int FISHING_ROD_REPAIR_AMOUNT = Items.FISHING_ROD.getDefaultInstance().getMaxDamage() / TOOL_REPAIR_PORTIONS;
    private static final int FLINT_AND_STEEL_REPAIR_AMOUNT = Items.FLINT_AND_STEEL.getDefaultInstance().getMaxDamage() / FLINT_AND_STEEL_REPAIR_PORTIONS;
    private static final int CROSSBOW_REPAIR_AMOUNT = Items.CROSSBOW.getDefaultInstance().getMaxDamage() / TOOL_REPAIR_PORTIONS;
    private static final int TRIDENT_REPAIR_AMOUNT = Items.TRIDENT.getDefaultInstance().getMaxDamage() / TOOL_REPAIR_PORTIONS;
    private static final int SHEARS_REPAIR_AMOUNT = Items.SHEARS.getDefaultInstance().getMaxDamage() / SHEARS_REPAIR_PORTIONS;
    private static final int BRUSH_REPAIR_AMOUNT = Items.BRUSH.getDefaultInstance().getMaxDamage() / TOOL_REPAIR_PORTIONS;
    private static final int MACE_REPAIR_AMOUNT = Items.MACE.getDefaultInstance().getMaxDamage() / TOOL_REPAIR_PORTIONS;
    private static final int REPAIR_COST = 2;
    private static final int COPY_ENCHANTS_COST = 25;
    private static final int COPY_ENCHANTS_TO_BOOK_COST = 1;
    private static final int MAX_BOOK_COPIES = 4;

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public void onAnvilUpdateEvent(AnvilUpdateEvent event)
    {
        ItemStack rightItemStack = event.getRight();
        Item rightItem = rightItemStack.getItem();
        ItemStack leftItemStack = event.getLeft();
        Item leftItem = leftItemStack.getItem();

        if (rightItem == Items.PHANTOM_MEMBRANE)
        {
            if (leftItem == Items.ELYTRA)
            {
                validateAndRepairCustom(leftItemStack, ELYTRA_REPAIR_AMOUNT, event);
                return;
            }
        }
        else if (rightItemStack.is(ItemTags.PLANKS))
        {
            if (leftItem == Items.SHIELD)
            {
                validateAndRepairCustom(leftItemStack, SHIELD_REPAIR_AMOUNT, event);
                return;
            }
        }

        if (leftItemStack.isDamageableItem())
        {
            if (validateAndRepair(leftItemStack, rightItem, event)) return;
        }

        if (rightItem == Items.IRON_INGOT)
        {
            if (leftItem == Items.SHEARS)
            {
                validateAndRepairCustom(leftItemStack, SHEARS_REPAIR_AMOUNT, event);
                return;
            }
        }
        else if (rightItem == Items.STRING)
        {
            if (leftItem == Items.BOW)
            {
                validateAndRepairCustom(leftItemStack, BOW_REPAIR_AMOUNT, event);
                return;
            }

            if (leftItem == Items.FISHING_ROD)
            {
                validateAndRepairCustom(leftItemStack, FISHING_ROD_REPAIR_AMOUNT, event);
                return;
            }

            if (leftItem == Items.CROSSBOW)
            {
                validateAndRepairCustom(leftItemStack, CROSSBOW_REPAIR_AMOUNT, event);
                return;
            }
        }
        else if (rightItem == Items.FEATHER)
        {
            if (leftItem == Items.BRUSH)
            {
                validateAndRepairCustom(leftItemStack, BRUSH_REPAIR_AMOUNT, event);
                return;
            }
        }

//        if (rightItem == Items.BREEZE_ROD)
//        {
//            if (leftItem == Items.MACE)
//            {
//                validateAndRepairCustom(leftItemStack, MACE_REPAIR_AMOUNT, event);
//            }
//        }

        else if (rightItem == Items.FLINT)
        {
            if (leftItem == Items.FLINT_AND_STEEL)
            {
                validateAndRepairCustom(leftItemStack, FLINT_AND_STEEL_REPAIR_AMOUNT, event);
                return;
            }
        }

        else if (rightItem == Items.PRISMARINE_SHARD)
        {
            if (leftItem == Items.TRIDENT)
            {
                validateAndRepairCustom(leftItemStack, TRIDENT_REPAIR_AMOUNT, event);
                return;
            }
        }

        else if (rightItem == Items.BOOK)
        {
            int rightItemStackCount = event.getRight().getCount();
            int booksToCopyAmount = Math.min(rightItemStackCount, MAX_BOOK_COPIES);

            if (leftItem == Items.ENCHANTED_BOOK)
            {
                ItemStack output = leftItemStack.copy();
                output.setCount(booksToCopyAmount + 1);
                event.setOutput(output);
                event.setMaterialCost(booksToCopyAmount);

                // see GrindstoneMenu.ctor().getExperienceFromItem()
                var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
                int cost = 0;
                for (var ench : enchantments.entrySet())
                {
                    var key = ench.getKey().value();
                    var value = ench.getIntValue();
                    if (!ench.getKey().value().isCurse())
                    {
                        cost += key.getMinCost(value) / 17;
                    }
                }

                if (cost < 1) cost = 1;
                event.setCost(booksToCopyAmount * cost);
            }
            else if (leftItemStack.isDamageableItem())
            {
                if (!leftItemStack.isEnchanted() || leftItemStack.isDamaged() || rightItemStack.isEnchanted())
                    return;

                var result = new ItemStack(Items.ENCHANTED_BOOK);
                var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
                EnchantmentHelper.setEnchantments(result, enchantments);
                event.setOutput(result);
                event.setCost(COPY_ENCHANTS_TO_BOOK_COST);
                event.setMaterialCost(1);
                return;
            }

            return;
        }

        if (leftItemStack.isDamageableItem() && leftItemStack.getDamageValue() == 0 &&
                rightItemStack.isEnchantable() && !rightItemStack.isEnchanted() && rightItemStack.getDamageValue() == 0)
        {
            boolean areItemsTheSame = rightItem.getName(rightItemStack).equals(leftItem.getName(leftItemStack));

            if (areItemsTheSame)
            {
                var result = rightItemStack.copy();
                var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
                EnchantmentHelper.setEnchantments(result, enchantments);
                result.setCount(2);
                event.setOutput(result);
                event.setCost(COPY_ENCHANTS_COST);
                return;
            }
        }
    }

    private boolean validateAndRepair(ItemStack itemToRepair, Item repairItem, final AnvilUpdateEvent event)
    {
        if (!isValidRepairItem(itemToRepair, repairItem) || itemToRepair.getDamageValue() == 0)
            return false;

        int repairAmount = itemToRepair.getMaxDamage() / EQUIPMENT_REPAIR_PORTIONS;
        setAndRepair(itemToRepair, repairAmount, event);
        return true;
    }

    private void validateAndRepairCustom(ItemStack itemToRepair, int repairAmountPerRepairItem, final AnvilUpdateEvent event)
    {
        if (itemToRepair.getDamageValue() == 0)
            return;

        setAndRepair(itemToRepair, repairAmountPerRepairItem, event);
    }

    private void setAndRepair(ItemStack itemToRepair, int repairAmountPerRepairItem, final AnvilUpdateEvent event)
    {
        int materialAmountAvailable = event.getRight().getCount();
        int materialAmountConsumed = calculateMaterialAmountConsumed(itemToRepair, repairAmountPerRepairItem, materialAmountAvailable);

        if (materialAmountConsumed <= 0) return;

        var result = itemToRepair.copy();
        repair(result, repairAmountPerRepairItem * materialAmountConsumed);
        event.setOutput(result);
        event.setCost(REPAIR_COST * materialAmountConsumed);
        event.setMaterialCost(materialAmountConsumed);
    }

    private void repair(ItemStack itemStack, int damageDecrement)
    {
        int newDamage = itemStack.getDamageValue() - damageDecrement;
        itemStack.setDamageValue(Math.max(newDamage, 0));
    }

    private boolean isValidRepairItem(ItemStack itemToRepair, Item repairItem)
    {
        return itemToRepair.getItem().isValidRepairItem(itemToRepair, new ItemStack(repairItem));
    }

    private int calculateMaterialAmountConsumed(ItemStack itemToRepair, int repairAmountPerRepairingItem, int materialAmountAvailable)
    {
        if (repairAmountPerRepairingItem <= 0 || materialAmountAvailable <= 0)
            return 0;

        int currentDamage = itemToRepair.getDamageValue();
        if (currentDamage <= 0)
            return 0;

        int needed = (currentDamage + repairAmountPerRepairingItem - 1) / repairAmountPerRepairingItem;

        return Math.min(needed, materialAmountAvailable);
    }
}
