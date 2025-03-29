package com.pekar.enchantonce.events;

import com.mojang.logging.LogUtils;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
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
    private static final int PER_BOOK_COPY_COST = 1;
    private static final int COPY_ENCHANTS_COST = 25;
    private static final int MOVE_ENCHANTS_COST = 5;
    private static final int COPY_ENCHANTS_TO_BOOK_COST = 1;

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public void onAnvilUpdateEvent(AnvilUpdateEvent event)
    {
        ItemStack rightItemStack = event.getRight();
        Item rightItem = rightItemStack.getItem();
        ItemStack leftItemStack = event.getLeft();
        Item leftItem = leftItemStack.getItem();

        if (leftItem instanceof ArmorItem || leftItem instanceof DiggerItem || leftItem instanceof SwordItem)
        {
            if (setOutputEquipment(event, leftItemStack, rightItem)) return;
        }

        if (rightItem == Items.IRON_INGOT)
        {
            if (leftItem == Items.SHEARS)
            {
                var result = leftItemStack.copy();
                repairItem(result, SHEARS_REPAIR_AMOUNT);
                event.setOutput(result);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.PHANTOM_MEMBRANE)
        {
            if (leftItem == Items.ELYTRA)
            {
                var result = leftItemStack.copy();
                repairItem(result, ELYTRA_REPAIR_AMOUNT);
                event.setOutput(result);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItemStack.is(ItemTags.PLANKS))
        {
            if (leftItem == Items.SHIELD)
            {
                var result = leftItemStack.copy();
                repairItem(result, SHIELD_REPAIR_AMOUNT);
                event.setOutput(result);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.STRING)
        {
            if (leftItem == Items.BOW)
            {
                var result = leftItemStack.copy();
                repairItem(result, BOW_REPAIR_AMOUNT);
                event.setOutput(result);
                event.setCost(REPAIR_COST);
                return;
            }

            if (leftItem == Items.FISHING_ROD)
            {
                var result = leftItemStack.copy();
                repairItem(result, FISHING_ROD_REPAIR_AMOUNT);
                event.setOutput(result);
                event.setCost(REPAIR_COST);
                return;
            }

            if (leftItem == Items.CROSSBOW)
            {
                var result = leftItemStack.copy();
                repairItem(result, CROSSBOW_REPAIR_AMOUNT);
                event.setOutput(result);
                event.setCost(REPAIR_COST);
                return;
            }

            return;
        }

        if (rightItem == Items.FEATHER)
        {
            if (leftItem == Items.BRUSH)
            {
                var result = leftItemStack.copy();
                repairItem(result, BRUSH_REPAIR_AMOUNT);
                event.setOutput(result);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.BREEZE_ROD)
        {
            if (leftItem == Items.MACE)
            {
                var result = leftItemStack.copy();
                repairItem(result, MACE_REPAIR_AMOUNT);
                event.setOutput(result);
                event.setCost(REPAIR_COST);
            }
        }

        if (rightItem == Items.FLINT)
        {
            if (leftItem == Items.FLINT_AND_STEEL)
            {
                var result = leftItemStack.copy();
                repairItem(result, FLINT_AND_STEEL_REPAIR_AMOUNT);
                event.setOutput(result);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.PRISMARINE_SHARD)
        {
            if (leftItem == Items.TRIDENT)
            {
                var result = leftItemStack.copy();
                repairItem(result, TRIDENT_REPAIR_AMOUNT);
                event.setOutput(result);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.BOOK)
        {
            int bookCount = Math.min(event.getRight().getCount() + 1, 5);

            if (leftItem == Items.ENCHANTED_BOOK)
            {
                ItemStack output = leftItemStack.copy();
                output.setCount(bookCount);
                event.setOutput(output);

                // see GrindstoneMenu.ctor().getExperienceFromItem()
                var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
                int cost = 0;
                for (var ench : enchantments.entrySet())
                {
                    var key = ench.getKey().value();
                    var value = ench.getIntValue();
                    if (!ench.getKey().is(EnchantmentTags.CURSE))
                    {
                        cost += key.getMinCost(value) / 17;
                    }
                }

                if (cost < 1) cost = 1;
                event.setCost((bookCount - 1) * cost);
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
                return;
            }

            return;
        }

        if (leftItemStack.isDamageableItem() && leftItemStack.getDamageValue() == 0 &&
                rightItemStack.isEnchantable() && !rightItemStack.isEnchanted())
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
            // THIS IS CHEATY BECAUSE OF DIFFERENT ENCHANTABILITY OF DIFFERENT MATERIALS
            /*
            else
            {
                var resultMap = new HashMap<Enchantment, Integer>();

                var enchantments = EnchantmentHelper.getEnchantments(leftItemStack);
                for (var ench : enchantments.entrySet())
                {
                    if (ench.getKey().category.canEnchant(rightItem))
                    {
                        resultMap.put(ench.getKey(), ench.getValue());
                    }
                }

                var result = rightItemStack.copy();

                EnchantmentHelper.setEnchantments(resultMap, result);
                result.setCount(1);
                event.setOutput(result);
                event.setCost(MOVE_ENCHANTS_COST);
                return;
            }
            */
        }
    }

    private boolean setOutputEquipment(AnvilUpdateEvent event, ItemStack itemStack, Item repairItem)
    {
        if (!isValidRepairItem(itemStack, repairItem))
            return false;

        var result = itemStack.copy();
        repairVanillaEquipment(result);
        event.setOutput(result);
        event.setCost(REPAIR_COST);
        return true;
    }

    private boolean repairVanillaEquipment(ItemStack itemToRepare)
    {
        int repairAmount = itemToRepare.getMaxDamage() / EQUIPMENT_REPAIR_PORTIONS;
        repairItem(itemToRepare, repairAmount);
        return true;
    }

    private void repairItem(ItemStack itemStack, int damageDecrement)
    {
        int newDamage = itemStack.getDamageValue() - damageDecrement;
        itemStack.setDamageValue(Math.max(newDamage, 0));
    }

    private boolean isValidRepairItem(ItemStack itemToRepair, Item repairItem)
    {
        // found in ItemStack (1.21.4)
        return itemToRepair.isValidRepairItem(new ItemStack(repairItem));
    }
}
