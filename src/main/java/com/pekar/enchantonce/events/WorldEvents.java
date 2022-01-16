package com.pekar.enchantonce.events;

import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.LinkedHashMap;

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

            return;
        }

        if (rightItem == Items.LEATHER)
        {
            if (leftItem instanceof ArmorItem)
            {
                setArmor(event, leftItemStack, Items.LEATHER);
            }
            if (leftItem == Items.ELYTRA)
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
            else if (leftItem == Items.SHIELD)
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

            if (resultItem instanceof TieredItem)
            {
                repairVanillaTool(resultItemStack, Items.DIAMOND);
                return;
            }

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

            if (resultItem == Items.SHEARS)
            {
                int repairAmount = resultItemStack.getMaxDamage();
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
