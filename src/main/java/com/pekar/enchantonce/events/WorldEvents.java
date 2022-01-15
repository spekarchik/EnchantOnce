package com.pekar.enchantonce.events;

import net.minecraft.world.item.*;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WorldEvents implements IEventHandler
{
    private static final int ELYTRA_REPAIR_AMOUNT = 100;
    private static final int SHIELD_REPAIR_AMOUNT = 100;
    private static final int BOW_REPAIR_AMOUNT = 70;
    private static final int FISHING_ROD_REPAIR_AMOUNT = 16;
    private static final int FLINT_AND_STEEL_REPAIR_AMOUNT = 15;
    private static final int TOOL_ENHANCEMENT_COST = 15;
    private static final int REPAIR_COST = 2;

    @SubscribeEvent
    public void onAnvilUpdateEvent(AnvilUpdateEvent event)
    {
        Item rightSlotItem = event.getRight().getItem();
        ItemStack left = event.getLeft();
        Item leftSlotItem = left.getItem();

        if (rightSlotItem == Items.DIAMOND)
        {
            /*if (leftSlotItem instanceof ItemTool)
            {
                ItemTool tool = (ItemTool)leftSlotItem;
                if (tool.getToolMaterialName().equals(Item.ToolMaterial.DIAMOND.toString()))
                {
                    event.setOutput(left);
                    event.setCost(REPAIR_COST);
                }
            }
            else*/ if (leftSlotItem == Items.DIAMOND_LEGGINGS || leftSlotItem == Items.DIAMOND_BOOTS
                    || leftSlotItem == Items.DIAMOND_CHESTPLATE || leftSlotItem == Items.DIAMOND_HELMET
                    || leftSlotItem == Items.DIAMOND_SWORD || leftSlotItem == Items.DIAMOND_PICKAXE
                    || leftSlotItem == Items.DIAMOND_HOE || leftSlotItem == Items.DIAMOND_SHOVEL
                    || leftSlotItem == Items.DIAMOND_AXE)
            {
                event.setOutput(left);
                event.setCost(REPAIR_COST);
            }

            return;
        }

//        if (rightSlotItem == Items.IRON_INGOT)
//        {
//            if (leftSlotItem instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)leftSlotItem;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.IRON.toString()))
//                {
//                    event.setOutput(left);
//                    event.setCost(REPAIR_COST);
//                }
//            }
//            else if (leftSlotItem == Items.IRON_LEGGINGS || leftSlotItem == Items.IRON_BOOTS
//                    || leftSlotItem == Items.IRON_CHESTPLATE || leftSlotItem == Items.IRON_HELMET
//                    || leftSlotItem == Items.IRON_SWORD || leftSlotItem == Items.SHEARS
//                    || leftSlotItem == Items.IRON_AXE || leftSlotItem == Items.IRON_PICKAXE
//                    || leftSlotItem == Items.IRON_HOE || leftSlotItem == Items.IRON_SHOVEL
//                    || leftSlotItem == Items.CHAINMAIL_LEGGINGS || leftSlotItem == Items.CHAINMAIL_BOOTS
//                    || leftSlotItem == Items.CHAINMAIL_CHESTPLATE || leftSlotItem == Items.CHAINMAIL_HELMET)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }

        if (rightSlotItem == Items.GOLD_INGOT)
        {
            /*if (leftSlotItem instanceof ItemTool)
            {
                ItemTool tool = (ItemTool)leftSlotItem;
                if (tool.getToolMaterialName().equals(Item.ToolMaterial.GOLD.toString()))
                {
                    event.setOutput(left);
                    event.setCost(REPAIR_COST);
                }
            }
            else*/ if (leftSlotItem == Items.GOLDEN_LEGGINGS || leftSlotItem == Items.GOLDEN_BOOTS
                    || leftSlotItem == Items.GOLDEN_CHESTPLATE || leftSlotItem == Items.GOLDEN_HELMET
                    || leftSlotItem == Items.GOLDEN_SWORD || leftSlotItem == Items.GOLDEN_PICKAXE
                    || leftSlotItem == Items.GOLDEN_HOE || leftSlotItem == Items.GOLDEN_SHOVEL
                    || leftSlotItem == Items.GOLDEN_AXE)
            {
                event.setOutput(left);
                event.setCost(REPAIR_COST);
            }

            return;
        }

//        if (rightSlotItem == Items.LEATHER)
//        {
//            if (leftSlotItem == Items.LEATHER_LEGGINGS || leftSlotItem == Items.LEATHER_BOOTS
//                    || leftSlotItem == Items.LEATHER_CHESTPLATE || leftSlotItem == Items.LEATHER_HELMET
//                    || leftSlotItem == Items.ELYTRA)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Item.getItemFromBlock(Blocks.COBBLESTONE))
//        {
//            if (leftSlotItem instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)leftSlotItem;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.STONE.toString()))
//                {
//                    event.setOutput(left);
//                    event.setCost(REPAIR_COST);
//                }
//            }
//            else if (leftSlotItem == Items.STONE_AXE || leftSlotItem == Items.STONE_PICKAXE
//                    || leftSlotItem == Items.STONE_HOE || leftSlotItem == Items.STONE_SHOVEL
//                    || leftSlotItem == Items.STONE_SWORD)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Item.getItemFromBlock(Blocks.PLANKS))
//        {
//            if (leftSlotItem instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)leftSlotItem;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.WOOD.toString())
//                    && leftSlotItem != ToolRegistry.ANCIENT_ROD)
//                {
//                    event.setOutput(left);
//                    event.setCost(REPAIR_COST);
//                }
//            }
//            else if (leftSlotItem == Items.WOODEN_AXE || leftSlotItem == Items.WOODEN_PICKAXE
//                    || leftSlotItem == Items.WOODEN_HOE || leftSlotItem == Items.WOODEN_SHOVEL
//                    || leftSlotItem == Items.WOODEN_SWORD || leftSlotItem == Items.SHIELD)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Items.STRING)
//        {
//            if (leftSlotItem == Items.BOW || leftSlotItem == Items.FISHING_ROD)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Items.FLINT)
//        {
//            if (leftSlotItem == Items.FLINT_AND_STEEL)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Items.BOOK)
//        {
//            int bookCount = Math.min(event.getRight().getCount() + 1, 5);
//
//            if (leftSlotItem == Items.ENCHANTED_BOOK)
//            {
//                ItemStack output = left.copy();
//                output.setCount(bookCount);
//                event.setOutput(output);
//                event.setCost(bookCount);
//            }
//
//            return;
//        }
    }

    @SubscribeEvent
    public void onAnvilRepairEvent(AnvilRepairEvent event)
    {
        ItemStack itemResult = event.getItemResult();
        Item item = itemResult.getItem();
        Item ingredient = event.getIngredientInput().getItem();

        if (ingredient == Items.DIAMOND)
        {
            if (item == Items.DIAMOND_LEGGINGS || item == Items.DIAMOND_BOOTS
                    || item == Items.DIAMOND_CHESTPLATE || item == Items.DIAMOND_HELMET)
            {
                repairVanillaArmor(itemResult);
                return;
            }

//            if (item instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)item;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.DIAMOND.toString()))
//                {
//                    int repairAmount = Item.ToolMaterial.DIAMOND.getMaxUses() / 6;
//                    repairItem(itemResult, repairAmount);
//                    return;
//                }
//            }

            if (item == Items.DIAMOND_PICKAXE || item == Items.DIAMOND_SHOVEL
                    || item == Items.DIAMOND_AXE || item == Items.DIAMOND_SWORD
                    || item == Items.DIAMOND_HOE)
            {
                repairVanillaTool(itemResult);
                return;
            }

            return;
        }

//        if (ingredient == Items.IRON_INGOT)
//        {
//            if (item == Items.IRON_LEGGINGS || item == Items.IRON_BOOTS
//                    || item == Items.IRON_CHESTPLATE || item == Items.IRON_HELMET
//                    || item == Items.CHAINMAIL_LEGGINGS || item == Items.CHAINMAIL_BOOTS
//                    || item == Items.CHAINMAIL_CHESTPLATE || item == Items.CHAINMAIL_HELMET)
//            {
//                repairVanillaArmor(itemResult, item);
//                return;
//            }
//
//            if (item instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)item;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.IRON.toString()))
//                {
//                    int repairAmount = Item.ToolMaterial.IRON.getMaxUses() / 6;
//                    repairItem(itemResult, repairAmount);
//                    return;
//                }
//            }
//
//            if (item == Items.IRON_PICKAXE || item == Items.IRON_SHOVEL
//                    || item == Items.IRON_AXE || item == Items.IRON_SWORD
//                    || item == Items.IRON_HOE || item == Items.SHEARS)
//            {
//                int repairAmount = Item.ToolMaterial.IRON.getMaxUses() / 6;
//                repairItem(itemResult, repairAmount);
//                return;
//            }
//
//            return;
//        }
//
//        if (ingredient == Item.getItemFromBlock(Blocks.COBBLESTONE))
//        {
//            if (item instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)item;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.STONE.toString()))
//                {
//                    int repairAmount = Item.ToolMaterial.STONE.getMaxUses() / 6;
//                    repairItem(itemResult, repairAmount);
//                    return;
//                }
//            }
//
//            if (item == Items.STONE_PICKAXE || item == Items.STONE_SHOVEL
//                    || item == Items.STONE_AXE || item == Items.STONE_SWORD
//                    || item == Items.STONE_HOE)
//            {
//                int repairAmount = Item.ToolMaterial.STONE.getMaxUses() / 6;
//                repairItem(itemResult, repairAmount);
//                return;
//            }
//
//            return;
//        }

        if (ingredient == Items.GOLD_INGOT)
        {
            if (item == Items.GOLDEN_LEGGINGS || item == Items.GOLDEN_BOOTS
                    || item == Items.GOLDEN_CHESTPLATE || item == Items.GOLDEN_HELMET)
            {
                repairVanillaArmor(itemResult);
                return;
            }

//            if (item instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)item;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.GOLD.toString()))
//                {
//                    int repairAmount = Item.ToolMaterial.GOLD.getMaxUses() / 4;
//                    repairItem(itemResult, repairAmount);
//                    return;
//                }
//            }

            if (item == Items.GOLDEN_PICKAXE || item == Items.GOLDEN_SHOVEL
                    || item == Items.GOLDEN_AXE || item == Items.GOLDEN_SWORD
                    || item == Items.GOLDEN_HOE)
            {
                repairVanillaTool(itemResult);
                return;
            }

            return;
        }

//        if (ingredient == Items.LEATHER)
//        {
//            if (item == Items.LEATHER_LEGGINGS || item == Items.LEATHER_BOOTS
//                    || item == Items.LEATHER_CHESTPLATE || item == Items.LEATHER_HELMET
//                    || item == Items.ELYTRA)
//            {
//                if (item instanceof ItemArmor)
//                {
//                    repairVanillaArmor(itemResult, item);
//                }
//                else
//                {
//                    repairItem(itemResult, ELYTRA_REPAIR_AMOUNT);
//                }
//                return;
//            }
//
//            return;
//        }
//
//        if (ingredient == Item.getItemFromBlock(Blocks.PLANKS))
//        {
//            if (item instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)item;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.WOOD.toString()))
//                {
//                    int repairAmount = Item.ToolMaterial.WOOD.getMaxUses() / 3;
//                    repairItem(itemResult, repairAmount);
//                    return;
//                }
//            }
//
//            if (item == Items.WOODEN_AXE || item == Items.WOODEN_PICKAXE
//                    || item == Items.WOODEN_HOE || item == Items.WOODEN_SHOVEL
//                    || item == Items.WOODEN_SWORD)
//            {
//                int repairAmount = Item.ToolMaterial.WOOD.getMaxUses() / 3;
//                repairItem(itemResult, repairAmount);
//                return;
//            }
//
//            if (item == Items.SHIELD)
//            {
//                repairItem(itemResult, SHIELD_REPAIR_AMOUNT);
//                return;
//            }
//            return;
//        }
//
//        if (ingredient == Items.STRING)
//        {
//            if (item == Items.BOW)
//            {
//                repairItem(itemResult, BOW_REPAIR_AMOUNT);
//                return;
//            }
//            else if (item == Items.FISHING_ROD)
//            {
//                repairItem(itemResult, FISHING_ROD_REPAIR_AMOUNT);
//                return;
//            }
//
//            return;
//        }
//
//        if (ingredient == Items.FLINT)
//        {
//            if (item == Items.FLINT_AND_STEEL)
//            {
//                repairItem(itemResult, FLINT_AND_STEEL_REPAIR_AMOUNT);
//                return;
//            }
//        }
    }

    private void repairVanillaArmor(ItemStack itemToRepare)
    {
        ArmorItem armor = (ArmorItem) itemToRepare.getItem();
        int repairAmount = armor.getMaterial().getDurabilityForSlot(armor.getSlot()) / 6;
        repairItem(itemToRepare, repairAmount);
    }

    private void repairVanillaTool(ItemStack itemToRepare)
    {
        int repairAmount = itemToRepare.getMaxDamage() / 6;
        repairItem(itemToRepare, repairAmount);
    }

//    private void repairArmor(ItemStack itemResult, Item item)
//    {
//        ModArmor armor = (ModArmor)item;
//        int repairAmount = armor.getRepairAmount();
//        repairItem(itemResult, repairAmount);
//    }

    private void repairItem(ItemStack itemStack, int damageDecrement)
    {
        int newDamage = itemStack.getDamageValue() - damageDecrement;
        itemStack.setDamageValue(newDamage < 0 ? 0 : newDamage);
    }
}
