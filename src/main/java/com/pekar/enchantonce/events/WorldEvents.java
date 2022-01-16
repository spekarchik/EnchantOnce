package com.pekar.enchantonce.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

public class WorldEvents implements IEventHandler
{
    private static final int ELYTRA_REPAIR_AMOUNT = 100;
    private static final int SHIELD_REPAIR_AMOUNT = 100;
    private static final int BOW_REPAIR_AMOUNT = 70;
    private static final int FISHING_ROD_REPAIR_AMOUNT = 16;
    private static final int FLINT_AND_STEEL_REPAIR_AMOUNT = 15;
    private static final int REPAIR_COST = 2;

    @SubscribeEvent
    public void onAnvilUpdateEvent(AnvilUpdateEvent event)
    {
        Item rightItem = event.getRight().getItem();
        ItemStack leftItemStack = event.getLeft();
        Item leftItem = leftItemStack.getItem();

        if (rightItem == Items.DIAMOND)
        {
            if (leftItem instanceof TieredItem)
            {
                setTool(event, leftItemStack, Items.DIAMOND);
            }
            else if (leftItem instanceof ArmorItem)
            {
                setArmor(event, leftItemStack, Items.DIAMOND);
            }
//            else if (leftItem == Items.DIAMOND_LEGGINGS || leftItem == Items.DIAMOND_BOOTS
//                    || leftItem == Items.DIAMOND_CHESTPLATE || leftItem == Items.DIAMOND_HELMET
//                    || leftItem == Items.DIAMOND_SWORD || leftItem == Items.DIAMOND_PICKAXE
//                    || leftItem == Items.DIAMOND_HOE || leftItem == Items.DIAMOND_SHOVEL
//                    || leftItem == Items.DIAMOND_AXE)
//            {
//                event.setOutput(leftItemStack);
//                event.setCost(REPAIR_COST);
//            }

            return;
        }

        if (rightItem == Items.IRON_INGOT)
        {
            if (leftItem instanceof TieredItem)
            {
                setTool(event, leftItemStack, Items.IRON_INGOT);
            }
            else if (leftItem instanceof ArmorItem)
            {
                setArmor(event, leftItemStack, Items.IRON_INGOT);
            }
            else if (leftItem == Items.SHEARS /*|| leftItem == Items.IRON_BOOTS
                    || leftItem == Items.IRON_CHESTPLATE || leftItem == Items.IRON_HELMET
                    || leftItem == Items.IRON_SWORD || leftItem == Items.IRON_LEGGINGS
                    || leftItem == Items.IRON_AXE || leftItem == Items.IRON_PICKAXE
                    || leftItem == Items.IRON_HOE || leftItem == Items.IRON_SHOVEL
                    || leftItem == Items.CHAINMAIL_LEGGINGS || leftItem == Items.CHAINMAIL_BOOTS
                    || leftItem == Items.CHAINMAIL_CHESTPLATE || leftItem == Items.CHAINMAIL_HELMET*/)
            {
                event.setOutput(leftItemStack);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.GOLD_INGOT)
        {
            if (leftItem instanceof TieredItem)
            {
                setTool(event, leftItemStack, Items.GOLD_INGOT);
            }
            else if (leftItem instanceof ArmorItem)
            {
                setArmor(event, leftItemStack, Items.GOLD_INGOT);
            }
//            else if (leftItem == Items.GOLDEN_LEGGINGS || leftItem == Items.GOLDEN_BOOTS
//                    || leftItem == Items.GOLDEN_CHESTPLATE || leftItem == Items.GOLDEN_HELMET
//                    || leftItem == Items.GOLDEN_SWORD || leftItem == Items.GOLDEN_PICKAXE
//                    || leftItem == Items.GOLDEN_HOE || leftItem == Items.GOLDEN_SHOVEL
//                    || leftItem == Items.GOLDEN_AXE)
//            {
//                event.setOutput(leftItemStack);
//                event.setCost(REPAIR_COST);
//            }

            return;
        }

        if (rightItem == Items.NETHERITE_INGOT)
        {
            if (leftItem instanceof TieredItem)
            {
                setTool(event, leftItemStack, Items.NETHERITE_INGOT);
            }
            else if (leftItem instanceof ArmorItem)
            {
                setArmor(event, leftItemStack, Items.NETHERITE_INGOT);
            }
//            else if (leftItem == Items.NETHERITE_LEGGINGS || leftItem == Items.NETHERITE_BOOTS
//                    || leftItem == Items.NETHERITE_CHESTPLATE || leftItem == Items.NETHERITE_HELMET
//                    || leftItem == Items.NETHERITE_SWORD || leftItem == Items.NETHERITE_PICKAXE
//                    || leftItem == Items.NETHERITE_HOE || leftItem == Items.NETHERITE_SHOVEL
//                    || leftItem == Items.NETHERITE_AXE)
//            {
//                event.setOutput(leftItemStack);
//                event.setCost(REPAIR_COST);
//            }

            return;
        }

        if (rightItem == Items.LEATHER)
        {
            if (leftItem instanceof ArmorItem)
            {
                setArmor(event, leftItemStack, Items.LEATHER);
            }
            if (leftItem == Items.ELYTRA /*|| leftItem == Items.LEATHER_BOOTS
                    || leftItem == Items.LEATHER_CHESTPLATE || leftItem == Items.LEATHER_HELMET
                    || leftItem == Items.LEATHER_LEGGINGS*/)
            {
                event.setOutput(leftItemStack);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.COBBLESTONE)
        {
            if (leftItem instanceof TieredItem)
            {
                setTool(event, leftItemStack, Items.COBBLESTONE);
            }
            else if (leftItem instanceof ArmorItem)
            {
                setArmor(event, leftItemStack, Items.COBBLESTONE);
            }
//            else if (leftItem == Items.STONE_AXE || leftItem == Items.STONE_PICKAXE
//                    || leftItem == Items.STONE_HOE || leftItem == Items.STONE_SHOVEL
//                    || leftItem == Items.STONE_SWORD)
//            {
//                event.setOutput(leftItemStack);
//                event.setCost(REPAIR_COST);
//            }

            return;
        }

        if (rightItem == Items.ACACIA_PLANKS || rightItem == Items.BIRCH_PLANKS
                || rightItem == Items.SPRUCE_PLANKS || rightItem == Items.OAK_PLANKS
                || rightItem == Items.DARK_OAK_PLANKS || rightItem == Items.JUNGLE_PLANKS
                || rightItem == Items.CRIMSON_PLANKS || rightItem == Items.WARPED_PLANKS)
        {
            if (leftItem instanceof TieredItem)
            {
                boolean b = setTool(event, leftItemStack, Items.OAK_PLANKS) || setTool(event, leftItemStack, Items.DARK_OAK_PLANKS)
                        || setTool(event, leftItemStack, Items.ACACIA_PLANKS) || setTool(event, leftItemStack, Items.BIRCH_PLANKS)
                        || setTool(event, leftItemStack, Items.JUNGLE_PLANKS) || setTool(event, leftItemStack, Items.SPRUCE_PLANKS)
                        || setTool(event, leftItemStack, Items.CRIMSON_PLANKS) || setTool(event, leftItemStack, Items.WARPED_PLANKS);
            }
            else if (/*leftItem == Items.WOODEN_AXE || leftItem == Items.WOODEN_PICKAXE
                    || leftItem == Items.WOODEN_HOE || leftItem == Items.WOODEN_SHOVEL
                    || leftItem == Items.WOODEN_SWORD ||*/ leftItem == Items.SHIELD)
            {
                event.setOutput(leftItemStack);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.STRING)
        {
            if (leftItem == Items.BOW || leftItem == Items.FISHING_ROD)
            {
                event.setOutput(leftItemStack);
                event.setCost(REPAIR_COST);
            }

            return;
        }

        if (rightItem == Items.FLINT)
        {
            if (event.getRight().getCount() >= 8)
            {
                if (leftItem.isEnchantable(leftItemStack))
                {
                    var output = leftItemStack.copy();
                    EnchantmentHelper.setEnchantments(new LinkedHashMap<>(), output);
                    event.setOutput(output);
                    event.setCost(1);
                }
            }
            else
            {
                if (leftItem == Items.FLINT_AND_STEEL)
                {
                    event.setOutput(leftItemStack);
                    event.setCost(REPAIR_COST);
                }
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
                event.setCost(bookCount);
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
        ItemStack leftItemStack = event.getItemInput();

        if (rightSlotItem == Items.DIAMOND)
        {
            if (resultItem instanceof ArmorItem)
            {
                repairVanillaArmor(resultItemStack, Items.DIAMOND);
                return;
            }
//            if (resultItem == Items.DIAMOND_LEGGINGS || resultItem == Items.DIAMOND_BOOTS
//                    || resultItem == Items.DIAMOND_CHESTPLATE || resultItem == Items.DIAMOND_HELMET)
//            {
//                repairVanillaArmor(resultItemStack, Items.DIAMOND);
//                return;
//            }

            if (resultItem instanceof TieredItem)
            {
                repairVanillaTool(resultItemStack, Items.DIAMOND);
                return;
            }

//            if (resultItem == Items.DIAMOND_PICKAXE || resultItem == Items.DIAMOND_SHOVEL
//                    || resultItem == Items.DIAMOND_AXE || resultItem == Items.DIAMOND_SWORD
//                    || resultItem == Items.DIAMOND_HOE)
//            {
//                repairVanillaTool(resultItemStack, Items.DIAMOND);
//                return;
//            }

            return;
        }

        if (rightSlotItem == Items.IRON_INGOT)
        {
            if (resultItem instanceof ArmorItem)
            {
                repairVanillaArmor(resultItemStack, Items.IRON_INGOT);
                return;
            }
//            if (resultItem == Items.IRON_LEGGINGS || resultItem == Items.IRON_BOOTS
//                    || resultItem == Items.IRON_CHESTPLATE || resultItem == Items.IRON_HELMET
//                    || resultItem == Items.CHAINMAIL_LEGGINGS || resultItem == Items.CHAINMAIL_BOOTS
//                    || resultItem == Items.CHAINMAIL_CHESTPLATE || resultItem == Items.CHAINMAIL_HELMET)
//            {
//                repairVanillaArmor(resultItemStack, resultItem);
//                return;
//            }

            if (resultItem instanceof TieredItem)
            {
                repairVanillaTool(resultItemStack, Items.IRON_INGOT);
                return;
            }

            if (/*resultItem == Items.IRON_PICKAXE || resultItem == Items.IRON_SHOVEL
                    || resultItem == Items.IRON_AXE || resultItem == Items.IRON_SWORD
                    || resultItem == Items.IRON_HOE ||*/ resultItem == Items.SHEARS)
            {
                int repairAmount = resultItemStack.getMaxDamage() / 5;
                repairItem(resultItemStack, repairAmount);
                return;
            }

            return;
        }

        if (rightSlotItem == Items.COBBLESTONE)
        {
            if (resultItem instanceof TieredItem)
            {
                repairVanillaTool(resultItemStack, Items.COBBLESTONE);
                return;
            }

            if (resultItem instanceof ArmorItem)
            {
                repairVanillaArmor(resultItemStack, Items.COBBLESTONE);
                return;
            }

//            if (resultItem == Items.STONE_PICKAXE || resultItem == Items.STONE_SHOVEL
//                    || resultItem == Items.STONE_AXE || resultItem == Items.STONE_SWORD
//                    || resultItem == Items.STONE_HOE)
//            {
//                int repairAmount = Item.ToolMaterial.STONE.getMaxUses() / 6;
//                repairItem(resultItemStack, repairAmount);
//                return;
//            }

            return;
        }

        if (rightSlotItem == Items.GOLD_INGOT)
        {
            if (resultItem instanceof ArmorItem)
            {
                repairVanillaArmor(resultItemStack, Items.GOLD_INGOT);
                return;
            }

            if (resultItem instanceof TieredItem)
            {
                repairVanillaTool(resultItemStack, Items.GOLD_INGOT);
            }

//            if (resultItem == Items.GOLDEN_PICKAXE || resultItem == Items.GOLDEN_SHOVEL
//                    || resultItem == Items.GOLDEN_AXE || resultItem == Items.GOLDEN_SWORD
//                    || resultItem == Items.GOLDEN_HOE)
//            {
//                repairVanillaTool(resultItemStack);
//                return;
//            }

            return;
        }

        if (rightSlotItem == Items.LEATHER)
        {
            if (resultItem instanceof ArmorItem)
            {
                repairVanillaArmor(resultItemStack, Items.LEATHER);
                return;
            }

            if (resultItem == Items.ELYTRA)
            {
                repairItem(resultItemStack, ELYTRA_REPAIR_AMOUNT);
                return;
            }

            return;
        }

        if (rightSlotItem == Items.ACACIA_PLANKS || rightSlotItem == Items.BIRCH_PLANKS
            || rightSlotItem == Items.OAK_PLANKS || rightSlotItem == Items.DARK_OAK_PLANKS
            || rightSlotItem == Items.JUNGLE_PLANKS || rightSlotItem == Items.SPRUCE_PLANKS
                || rightSlotItem == Items.CRIMSON_PLANKS || rightSlotItem == Items.WARPED_PLANKS)
        {
            if (resultItem instanceof TieredItem)
            {
                boolean b = repairVanillaTool(resultItemStack, Items.ACACIA_PLANKS)
                        || repairVanillaTool(resultItemStack, Items.BIRCH_PLANKS)
                        || repairVanillaTool(resultItemStack, Items.OAK_PLANKS)
                        || repairVanillaTool(resultItemStack, Items.DARK_OAK_PLANKS)
                        || repairVanillaTool(resultItemStack, Items.JUNGLE_PLANKS)
                        || repairVanillaTool(resultItemStack, Items.SPRUCE_PLANKS)
                        || repairVanillaTool(resultItemStack, Items.CRIMSON_PLANKS)
                        || repairVanillaTool(resultItemStack, Items.WARPED_PLANKS);
            }

            if (resultItem == Items.SHIELD)
            {
                repairItem(resultItemStack, SHIELD_REPAIR_AMOUNT);
                return;
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
            else if (resultItem == Items.FISHING_ROD)
            {
                repairItem(resultItemStack, FISHING_ROD_REPAIR_AMOUNT);
                return;
            }

            return;
        }

        if (rightSlotItem == Items.FLINT)
        {
            if (resultItem == Items.FLINT_AND_STEEL && event.getIngredientInput().getCount() < 8)
            {
                repairItem(resultItemStack, FLINT_AND_STEEL_REPAIR_AMOUNT);
                return;
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

    private void setArmor(AnvilUpdateEvent event, ItemStack armorItemStack, Item repairItem)
    {
        if (!isValidArmorRepairItem(armorItemStack.getItem(), repairItem))
            return;

        event.setOutput(armorItemStack);
        event.setCost(REPAIR_COST);
    }

    private void repairVanillaArmor(ItemStack itemToRepare, Item repairItem)
    {
        if (!isValidArmorRepairItem(itemToRepare.getItem(), repairItem)) return;

        var armor = (ArmorItem) itemToRepare.getItem();
        int repairAmount = armor.getMaterial().getDurabilityForSlot(armor.getSlot()) / 5;
        repairItem(itemToRepare, repairAmount);
    }

    private boolean repairVanillaTool(ItemStack itemToRepare, Item repairItem)
    {
        if (!isValidToolRepairItem(itemToRepare.getItem(), repairItem)) return false;

        int repairAmount = itemToRepare.getMaxDamage() / 5;
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
