package com.pekar.enchantonce.events;

import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.LinkedHashMap;

public class WorldEvents implements IEventHandler
{
    private static final int ARMOR_REPAIR_PORTIONS = 5;
    private static final int TOOL_REPAIR_PORTIONS = 5;
    private static final int ELITRA_REPAIR_PORTIONS = 1;
    private static final int SHIELD_REPAIR_PORTIONS = 2;
    private static final int FLINT_AND_STEEL_REPAIR_PORTIONS = 1;
    private static final int SHEARS_REPAIR_PORTIONS = 1;
    private static final int ELYTRA_REPAIR_AMOUNT = Items.ELYTRA.getMaxDamage(null)  / ELITRA_REPAIR_PORTIONS;
    private static final int SHIELD_REPAIR_AMOUNT = Items.SHIELD.getMaxDamage(null) / SHIELD_REPAIR_PORTIONS;
    private static final int BOW_REPAIR_AMOUNT = Items.BOW.getMaxDamage(null) / TOOL_REPAIR_PORTIONS;
    private static final int FISHING_ROD_REPAIR_AMOUNT = Items.FISHING_ROD.getMaxDamage(null) / TOOL_REPAIR_PORTIONS;
    private static final int FLINT_AND_STEEL_REPAIR_AMOUNT = Items.FLINT_AND_STEEL.getMaxDamage(null) / FLINT_AND_STEEL_REPAIR_PORTIONS;
    private static final int CROSSBOW_REPAIR_AMOUNT = Items.CROSSBOW.getMaxDamage(null) / TOOL_REPAIR_PORTIONS;
    private static final int TRIDENT_REPAIR_AMOUNT = Items.TRIDENT.getMaxDamage(null) / TOOL_REPAIR_PORTIONS;
    private static final int SHEARS_REPAIR_AMOUNT = Items.SHEARS.getMaxDamage(null) / SHEARS_REPAIR_PORTIONS;
    private static final int REPAIR_COST = 2;
    private static final int PER_BOOK_COPY_COST = 1;
    private static final int COPY_ENCHANTS_COST = 25;
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

            if (rightItem.getRegistryName().equals(leftItem.getRegistryName()) && leftItemStack.getDamageValue() == 0)
            {
                if (rightItemStack.isEnchanted()) return;

                var result = leftItemStack.copy();
                result.setCount(2);
                event.setOutput(result);
                event.setCost(COPY_ENCHANTS_COST);
                return;
            }
        }

        if (leftItem instanceof ArmorItem)
        {
            if (setArmor(event, leftItemStack, rightItem)) return;

            if (rightItem.getRegistryName().equals(leftItem.getRegistryName()) && leftItemStack.getDamageValue() == 0)
            {
                if (rightItemStack.isEnchanted()) return;

                var result = leftItemStack.copy();
                result.setCount(2);
                event.setOutput(result);
                event.setCost(COPY_ENCHANTS_COST);
                return;
            }
        }

        if (rightItem == Items.IRON_INGOT)
        {
            if (leftItem == Items.SHEARS)
            {
                event.setOutput(leftItemStack);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.PHANTOM_MEMBRANE)
        {
            if (leftItem == Items.ELYTRA)
            {
                event.setOutput(leftItemStack);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.ACACIA_PLANKS || rightItem == Items.BIRCH_PLANKS
                || rightItem == Items.SPRUCE_PLANKS || rightItem == Items.OAK_PLANKS
                || rightItem == Items.DARK_OAK_PLANKS || rightItem == Items.JUNGLE_PLANKS
                || rightItem == Items.CRIMSON_PLANKS || rightItem == Items.WARPED_PLANKS)
        {
            if (leftItem == Items.SHIELD)
            {
                event.setOutput(leftItemStack);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.STRING)
        {
            if (leftItem == Items.BOW || leftItem == Items.FISHING_ROD || leftItem == Items.CROSSBOW)
            {
                event.setOutput(leftItemStack);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.FLINT)
        {
            if (leftItem == Items.FLINT_AND_STEEL)
            {
                event.setOutput(leftItemStack);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.PRISMARINE_SHARD)
        {
            if (leftItem == Items.TRIDENT)
            {
                event.setOutput(leftItemStack);
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
                event.setCost((bookCount - 1) * PER_BOOK_COPY_COST);
            }
            else if ((leftItem instanceof TieredItem) || (leftItem instanceof ArmorItem))
            {
                if (!leftItemStack.isEnchanted() || leftItemStack.isDamaged() || rightItemStack.isEnchanted())
                    return;

                var result = new ItemStack(Items.ENCHANTED_BOOK);
                var enchantments = EnchantmentHelper.getEnchantments(leftItemStack);
                EnchantmentHelper.setEnchantments(enchantments, result);
                event.setOutput(result);
                event.setCost(COPY_ENCHANTS_TO_BOOK_COST);
                return;
            }

            return;
        }
    }

    @SubscribeEvent
    public void onAnvilRepairEvent(AnvilRepairEvent event)
    {
        ItemStack resultItemStack = event.getItemResult();
        Item resultItem = resultItemStack.getItem();
        Item rightSlotItem = event.getIngredientInput().getItem();

        if (resultItem instanceof ArmorItem)
        {
            if (repairVanillaArmor(resultItemStack, rightSlotItem)) return;
        }

        if (resultItem instanceof TieredItem)
        {
            if (repairVanillaTool(resultItemStack, rightSlotItem)) return;
        }

        if (rightSlotItem == Items.IRON_INGOT)
        {
            if (resultItem == Items.SHEARS)
            {
                repairItem(resultItemStack, SHEARS_REPAIR_AMOUNT);
            }

            return;
        }

        if (rightSlotItem == Items.PHANTOM_MEMBRANE)
        {
            if (resultItem == Items.ELYTRA)
            {
                repairItem(resultItemStack, ELYTRA_REPAIR_AMOUNT);
            }

            return;
        }

        if (rightSlotItem == Items.ACACIA_PLANKS || rightSlotItem == Items.BIRCH_PLANKS
            || rightSlotItem == Items.OAK_PLANKS || rightSlotItem == Items.DARK_OAK_PLANKS
            || rightSlotItem == Items.JUNGLE_PLANKS || rightSlotItem == Items.SPRUCE_PLANKS
                || rightSlotItem == Items.CRIMSON_PLANKS || rightSlotItem == Items.WARPED_PLANKS)
        {
            if (resultItem == Items.SHIELD)
            {
                repairItem(resultItemStack, SHIELD_REPAIR_AMOUNT);
            }

            return;
        }

        if (rightSlotItem == Items.STRING)
        {
            if (resultItem == Items.BOW)
            {
                repairItem(resultItemStack, BOW_REPAIR_AMOUNT);
                return;
            }

            if (resultItem == Items.FISHING_ROD)
            {
                repairItem(resultItemStack, FISHING_ROD_REPAIR_AMOUNT);
                return;
            }

            if (resultItem == Items.CROSSBOW)
            {
                repairItem(resultItemStack, CROSSBOW_REPAIR_AMOUNT);
                return;
            }

            return;
        }

        if (rightSlotItem == Items.FLINT)
        {
            if (resultItem == Items.FLINT_AND_STEEL && event.getIngredientInput().getCount() < 8)
            {
                repairItem(resultItemStack, FLINT_AND_STEEL_REPAIR_AMOUNT);
            }

            return;
        }

        if (rightSlotItem == Items.PRISMARINE_SHARD)
        {
            if (resultItem == Items.TRIDENT)
            {
                repairItem(resultItemStack, TRIDENT_REPAIR_AMOUNT);
            }
        }
    }

    private boolean setTool(AnvilUpdateEvent event, ItemStack toolItemStack, Item repairItem)
    {
        if (!isValidToolRepairItem(toolItemStack.getItem(), repairItem))
            return false;

        event.setOutput(toolItemStack);
        event.setCost(REPAIR_COST);
        return true;
    }

    private boolean setArmor(AnvilUpdateEvent event, ItemStack armorItemStack, Item repairItem)
    {
        if (!isValidArmorRepairItem(armorItemStack.getItem(), repairItem))
            return false;

        event.setOutput(armorItemStack);
        event.setCost(REPAIR_COST);
        return true;
    }

    private boolean repairVanillaArmor(ItemStack itemToRepare, Item repairItem)
    {
        if (!isValidArmorRepairItem(itemToRepare.getItem(), repairItem)) return false;

        var armor = (ArmorItem) itemToRepare.getItem();
        int repairAmount = armor.getMaterial().getDurabilityForSlot(armor.getSlot()) / ARMOR_REPAIR_PORTIONS;
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
