package com.pekar.enchantonce.events;

import com.mojang.logging.LogUtils;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
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
    private static final int ELYTRA_REPAIR_AMOUNT = getRepairAmount(Items.ELYTRA.getDefaultInstance().getMaxDamage(), ELYTRA_REPAIR_PORTIONS);
    private static final int SHIELD_REPAIR_AMOUNT = getRepairAmount(Items.SHIELD.getDefaultInstance().getMaxDamage(), SHIELD_REPAIR_PORTIONS);
    private static final int BOW_REPAIR_AMOUNT = getRepairAmount(Items.BOW.getDefaultInstance().getMaxDamage(), TOOL_REPAIR_PORTIONS);
    private static final int FISHING_ROD_REPAIR_AMOUNT = getRepairAmount(Items.FISHING_ROD.getDefaultInstance().getMaxDamage(), TOOL_REPAIR_PORTIONS);
    private static final int FLINT_AND_STEEL_REPAIR_AMOUNT = getRepairAmount(Items.FLINT_AND_STEEL.getDefaultInstance().getMaxDamage(), FLINT_AND_STEEL_REPAIR_PORTIONS);
    private static final int CROSSBOW_REPAIR_AMOUNT = getRepairAmount(Items.CROSSBOW.getDefaultInstance().getMaxDamage(), TOOL_REPAIR_PORTIONS);
    private static final int TRIDENT_REPAIR_AMOUNT = getRepairAmount(Items.TRIDENT.getDefaultInstance().getMaxDamage(), TOOL_REPAIR_PORTIONS);
    private static final int SHEARS_REPAIR_AMOUNT = getRepairAmount(Items.SHEARS.getDefaultInstance().getMaxDamage(), SHEARS_REPAIR_PORTIONS);
    private static final int BRUSH_REPAIR_AMOUNT = getRepairAmount(Items.BRUSH.getDefaultInstance().getMaxDamage(), TOOL_REPAIR_PORTIONS);
    private static final int MACE_REPAIR_AMOUNT = getRepairAmount(Items.MACE.getDefaultInstance().getMaxDamage(), TOOL_REPAIR_PORTIONS);
    private static final int REPAIR_COST = 2;
    private static final int COPY_ENCHANTS_COST = 25;
    private static final int COPY_ENCHANTS_TO_BOOK_COST = 1;
    private static final int MAX_BOOK_COPIES = 4;

    private static final Logger LOGGER = LogUtils.getLogger();

    private static int getRepairAmount(int maxDamage, int portions)
    {
        return (maxDamage + portions - 1) / portions;
    }

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

            if (leftItem == Items.ENCHANTED_BOOK)
            {
                // the result is an echanted book with enchantments that have one step lower level than the input book
                var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
                var resultEnchantments = new ItemEnchantments.Mutable(enchantments);
                boolean changed = false;

                // modify the existing enchantment collection in-place: lower each level by 1, remove if becomes 0
                for (var entry : enchantments.entrySet())
                {
                    var key = entry.getKey();
                    int level = entry.getIntValue();

                    // Do not touch curses: keep them intact
                    if (key.is(EnchantmentTags.CURSE)) continue;

                    int newLevel = level - 1;
                    if (newLevel >= 0)
                    {
                        resultEnchantments.set(key, newLevel);
                        changed = true;
                    }
                }

                if (!changed || resultEnchantments.keySet().isEmpty())
                {
                    event.setCanceled(true);
                    return;
                }

                var result = leftItemStack.copy();
                EnchantmentHelper.setEnchantments(result, resultEnchantments.toImmutable());
                event.setOutput(result);
                event.setMaterialCost(1);
                event.setCost(1);
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
                    if (!ench.getKey().is(EnchantmentTags.CURSE))
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

        if (rightItem == Items.ENCHANTED_BOOK && leftItem == Items.ENCHANTED_BOOK)
        {
            var leftEnchs = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
            var rightEnchs = EnchantmentHelper.getEnchantmentsForCrafting(rightItemStack);

            var leftEnchMutable = new ItemEnchantments.Mutable(leftEnchs);
            boolean changed = false;

            // Merge enchantments but do not increase any enchantment level beyond the highest level present in inputs.
            // Merge into the left enchantment collection in-place. We cast to java.util.Map when
            // using get/put to avoid trying to construct incompatible java.util collections from
            // Minecraft/fastutil types.
            for (var entry : rightEnchs.entrySet())
            {
                var key = entry.getKey();

                boolean areEnchantmentsCompatible = EnchantmentHelper.isEnchantmentCompatible(leftEnchs.keySet(), key);
                boolean areEnchantmentsAlreadyPresent = leftEnchs.keySet().contains(key);
                boolean canEnchant = areEnchantmentsCompatible || areEnchantmentsAlreadyPresent;

                if (!canEnchant) continue;

                int rightLevel = entry.getIntValue();
                int leftLevel = leftEnchMutable.getLevel(key);
                int finalLevel = Math.max(leftLevel, rightLevel);
                leftEnchMutable.set(key, finalLevel);
                if (finalLevel != leftLevel) changed = true;
            }

            if (!changed)
            {
                event.setCanceled(true);
                return;
            }

            var result = leftItemStack.copy();
            EnchantmentHelper.setEnchantments(result, leftEnchMutable.toImmutable());
            event.setOutput(result);
            event.setMaterialCost(1);
            return;
        }

        if (leftItemStack.isDamageableItem() && leftItemStack.getDamageValue() == 0 &&
                rightItemStack.isEnchantable() && !rightItemStack.isEnchanted() && rightItemStack.getDamageValue() == 0)
        {
            boolean areItemsTheSame = rightItem.getName(rightItemStack).equals(leftItem.getName(leftItemStack));

            if (areItemsTheSame)
            {
                var result = leftItemStack.copy();
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

        if (leftItem != Items.ENCHANTED_BOOK && leftItemStack.isEnchanted() && (rightItemStack.isEnchanted() || rightItem == Items.ENCHANTED_BOOK))
        {
            boolean areItemsTheSame = rightItem.getName(rightItemStack).equals(leftItem.getName(leftItemStack));

            if (areItemsTheSame || rightItem == Items.ENCHANTED_BOOK)
            {
                var leftEnchs = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
                var rightEnchs = EnchantmentHelper.getEnchantmentsForCrafting(rightItemStack);

                var leftEnchMutable = new ItemEnchantments.Mutable(leftEnchs);
                boolean changed = false;

                for (var entry : rightEnchs.entrySet())
                {
                    var key = entry.getKey();
                    boolean isEnchantmentSupportedByItem = key.value().definition().supportedItems().contains(leftItemStack.getItemHolder());
                    boolean areEnchantmentsCompatible = EnchantmentHelper.isEnchantmentCompatible(leftEnchs.keySet(), key);
                    boolean areEnchantmentsAlreadyPresent = leftEnchs.keySet().contains(key);
                    boolean canEnchant = isEnchantmentSupportedByItem && (areEnchantmentsCompatible || areEnchantmentsAlreadyPresent);

                    if (!canEnchant) continue;

                    int rightLevel = entry.getIntValue();
                    int leftLevel = leftEnchMutable.getLevel(key);
                    int finalLevel = Math.max(leftLevel, rightLevel);
                    leftEnchMutable.set(key, finalLevel);
                    if (finalLevel != leftLevel) changed = true;
                }

                var result = event.getOutput().copy();

                boolean durabilityChanged = leftItemStack.isDamageableItem() && rightItemStack.isDamageableItem()
                        && (leftItemStack.getDamageValue() != result.getDamageValue() || rightItemStack.getDamageValue() != result.getDamageValue());

                if (!changed && !durabilityChanged)
                {
                    event.setCanceled(true);
                    return;
                }

                EnchantmentHelper.setEnchantments(result, leftEnchMutable.toImmutable());
                event.setOutput(result);
                event.setMaterialCost(1);
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
        int materialNumberConsumed = calculateMaterialNumberConsumed(itemToRepair, repairAmountPerRepairItem, materialAmountAvailable);

        if (materialNumberConsumed <= 0) return;

        var result = itemToRepair.copy();
        repair(result, repairAmountPerRepairItem * materialNumberConsumed);
        event.setOutput(result);
        event.setCost(REPAIR_COST * materialNumberConsumed);
        event.setMaterialCost(materialNumberConsumed);
    }

    private void repair(ItemStack itemStack, int damageDecrement)
    {
        int newDamage = itemStack.getDamageValue() - damageDecrement;
        itemStack.setDamageValue(Math.max(newDamage, 0));
    }

    private boolean isValidRepairItem(ItemStack itemToRepair, Item repairItem)
    {
        // found in ItemStack (1.21.4)
        return itemToRepair.isValidRepairItem(new ItemStack(repairItem));
    }

    private int calculateMaterialNumberConsumed(ItemStack itemToRepair, int repairAmountPerRepairingItem, int materialNumberAvailable)
    {
        if (repairAmountPerRepairingItem <= 0 || materialNumberAvailable <= 0)
            return 0;

        int currentDamage = itemToRepair.getDamageValue();
        if (currentDamage <= 0)
            return 0;

        int needed = (currentDamage + repairAmountPerRepairingItem - 1) / repairAmountPerRepairingItem;

        return Math.min(needed, materialNumberAvailable);
    }
}
