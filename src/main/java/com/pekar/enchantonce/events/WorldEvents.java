package com.pekar.enchantonce.events;

import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

public class WorldEvents implements IEventHandler
{
    private static final int ARMOR_REPAIR_PORTIONS = 5;
    private static final int TOOL_REPAIR_PORTIONS = 5;
    private static final int ELYTRA_REPAIR_PORTIONS = 1;
    private static final int SHIELD_REPAIR_PORTIONS = 2;
    private static final int FLINT_AND_STEEL_REPAIR_PORTIONS = 1;
    private static final int SHEARS_REPAIR_PORTIONS = 1;
    private static final int BRUSH_REPAIR_PORTIONS = 4;
    private static final int ELYTRA_REPAIR_AMOUNT = Items.ELYTRA.getMaxDamage(null)  / ELYTRA_REPAIR_PORTIONS;
    private static final int SHIELD_REPAIR_AMOUNT = Items.SHIELD.getMaxDamage(null) / SHIELD_REPAIR_PORTIONS;
    private static final int BOW_REPAIR_AMOUNT = Items.BOW.getMaxDamage(null) / TOOL_REPAIR_PORTIONS;
    private static final int FISHING_ROD_REPAIR_AMOUNT = Items.FISHING_ROD.getMaxDamage(null) / TOOL_REPAIR_PORTIONS;
    private static final int FLINT_AND_STEEL_REPAIR_AMOUNT = Items.FLINT_AND_STEEL.getMaxDamage(null) / FLINT_AND_STEEL_REPAIR_PORTIONS;
    private static final int CROSSBOW_REPAIR_AMOUNT = Items.CROSSBOW.getMaxDamage(null) / TOOL_REPAIR_PORTIONS;
    private static final int TRIDENT_REPAIR_AMOUNT = Items.TRIDENT.getMaxDamage(null) / TOOL_REPAIR_PORTIONS;
    private static final int SHEARS_REPAIR_AMOUNT = Items.SHEARS.getMaxDamage(null) / SHEARS_REPAIR_PORTIONS;
    private static final int BRUSH_REPAIR_AMOUNT = Items.BRUSH.getMaxDamage(null) / BRUSH_REPAIR_PORTIONS;
    private static final int MACE_REPAIR_AMOUNT = Items.MACE.getMaxDamage(null) / TOOL_REPAIR_PORTIONS;
    private static final int REPAIR_COST = 2;
    private static final int PER_BOOK_COPY_COST = 1;
    private static final int COPY_ENCHANTS_COST = 25;
    private static final int MOVE_ENCHANTS_COST = 5;
    private static final int COPY_ENCHANTS_TO_BOOK_COST = 1;

    @SubscribeEvent
    public void onAnvilUpdateEvent(AnvilUpdateEvent event)
    {
        ItemStack rightItemStack = event.getRight();
        Item rightItem = rightItemStack.getItem();
        ItemStack leftItemStack = event.getLeft();
        Item leftItem = leftItemStack.getItem();

        if (leftItem instanceof TieredItem)
        {
            if (setTool(event, leftItemStack, rightItem)) return;
        }

        if (leftItem instanceof ArmorItem)
        {
            if (setArmor(event, leftItemStack, rightItem)) return;
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

        if (rightItem == Items.ACACIA_PLANKS || rightItem == Items.BIRCH_PLANKS
                || rightItem == Items.SPRUCE_PLANKS || rightItem == Items.OAK_PLANKS
                || rightItem == Items.DARK_OAK_PLANKS || rightItem == Items.JUNGLE_PLANKS
                || rightItem == Items.CRIMSON_PLANKS || rightItem == Items.WARPED_PLANKS
                || rightItem == Items.MANGROVE_PLANKS || rightItem == Items.BAMBOO_PLANKS
                || rightItem == Items.CHERRY_PLANKS)
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

    private boolean setTool(AnvilUpdateEvent event, ItemStack toolItemStack, Item repairItem)
    {
        if (!isValidToolRepairItem(toolItemStack.getItem(), repairItem))
            return false;

        var result = toolItemStack.copy();
        repairVanillaTool(result, repairItem);
        event.setOutput(result);
        event.setCost(REPAIR_COST);
        return true;
    }

    private boolean setArmor(AnvilUpdateEvent event, ItemStack armorItemStack, Item repairItem)
    {
        if (!isValidArmorRepairItem(armorItemStack.getItem(), repairItem))
            return false;

        var result = armorItemStack.copy();
        repairVanillaArmor(result, repairItem);
        event.setOutput(result);
        event.setCost(REPAIR_COST);
        return true;
    }

    private boolean repairVanillaArmor(ItemStack itemToRepare, Item repairItem)
    {
        if (!isValidArmorRepairItem(itemToRepare.getItem(), repairItem)) return false;

        var armor = (ArmorItem) itemToRepare.getItem();
        int repairAmount = armor.getMaterial().value().getDefense(armor.getType()) / ARMOR_REPAIR_PORTIONS;
        repairItem(itemToRepare, repairAmount);
        return true;
    }

    private boolean repairVanillaTool(ItemStack itemToRepare, Item repairItem)
    {
        if (!isValidToolRepairItem(itemToRepare.getItem(), repairItem)) return false;

        int repairAmount = itemToRepare.getMaxDamage() / TOOL_REPAIR_PORTIONS;
        repairItem(itemToRepare, repairAmount);
        return true;
    }

    private void repairItem(ItemStack itemStack, int damageDecrement)
    {
        int newDamage = itemStack.getDamageValue() - damageDecrement;
        itemStack.setDamageValue(newDamage < 0 ? 0 : newDamage);
    }

    private boolean isValidToolRepairItem(Item toolItem, Item repairItem)
    {
        if (!(toolItem instanceof TieredItem)) return false;

        var tool = (TieredItem) toolItem;
        return tool.getTier().getRepairIngredient().test(new ItemStack(repairItem));
    }

    private boolean isValidArmorRepairItem(Item armorItem, Item repairItem)
    {
        if (!(armorItem instanceof ArmorItem)) return false;

        var armor = (ArmorItem) armorItem;
        return armor.isValidRepairItem(null, new ItemStack(repairItem));
    }
}
