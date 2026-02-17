package com.pekar.enchantonce.events;

import com.mojang.logging.LogUtils;
import com.pekar.enchantonce.enchantments.EnchantmentRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.function.Predicate;

import static com.pekar.enchantonce.Main.MODID;

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
    private static final int REPAIR_COST = 2;
    private static final int COPY_ENCHANTS_COST = 25;
    private static final int COPY_ENCHANTS_TO_BOOK_COST = 1;
    private static final int MAX_BOOK_COPIES = 4;

    private static final TagKey<Enchantment> PERSISTENT = TagKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(MODID, "persistent"));

    private static final Logger LOGGER = LogUtils.getLogger();

    private static int getRepairAmount(int maxDamage, int portions)
    {
        return (maxDamage + portions - 1) / portions;
    }

    @SubscribeEvent
    public void onAnvilUpdateEvent(AnvilUpdateEvent event)
    {
        if (event.getPlayer().level().isClientSide()) return;

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
                decreaseBookEnchantments(event);
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
                copyEnchantedBook(event, booksToCopyAmount);
            }
            else if (leftItemStack.isDamageableItem())
            {
                if (!leftItemStack.isEnchanted() || leftItemStack.isDamaged() || rightItemStack.isEnchanted())
                    return;

                moveEnchantmentsToBook(event);
                return;
            }

            return;
        }

        if (rightItem == Items.ENCHANTED_BOOK && leftItem == Items.ENCHANTED_BOOK)
        {
            combineEnchantedBooks(event);
            return;
        }

        if (leftItemStack.isDamageableItem() && leftItemStack.getDamageValue() == 0 &&
                rightItemStack.isEnchantable() && !rightItemStack.isEnchanted() && rightItemStack.getDamageValue() == 0)
        {
            boolean areItemsTheSame = rightItem.getName(rightItemStack).equals(leftItem.getName(leftItemStack));

            if (areItemsTheSame)
            {
                copyEnchantedGear(event);
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
                combineEnchantedItems(event);
                return;
            }
        }

        if (leftItemStack.isEnchantable() && !leftItemStack.is(Items.ENCHANTED_BOOK)
                && rightItemStack.is(Items.ENCHANTED_BOOK) && !leftItemStack.isEnchanted())
        {
            enchantGearWithBook(event);
            return;
        }
    }

    private static void decreaseBookEnchantments(AnvilUpdateEvent event)
    {
        var leftItemStack = event.getLeft();
        var rightItemStack = event.getRight();
        var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
        var resultEnchantments = new ItemEnchantments.Mutable(enchantments);
        boolean changed = false;
        int flintsAvailable = rightItemStack.getCount();
        int maxLevel = 0;
        boolean hasCurses = false;

        for (var entry : enchantments.entrySet())
        {
            var key = entry.getKey();
            int level = entry.getIntValue();
            maxLevel = Math.max(maxLevel, level);
            if (key.is(EnchantmentTags.CURSE)) hasCurses = true;
        }

        int levelsToRemove = hasCurses ? maxLevel : (maxLevel - 1); // we can remove all enchantments and keep only curses
        int flintsConsumed = Math.min(levelsToRemove, flintsAvailable);

        // modify the existing enchantment collection in-place: lower each level by 1, remove if becomes 0
        for (var entry : enchantments.entrySet())
        {
            var key = entry.getKey();

            // Do not touch curses: keep them intact
            if (key.is(PERSISTENT)) continue;

            int level = entry.getIntValue();

            int newLevel = Math.max(0, level - flintsConsumed);
            resultEnchantments.set(key, newLevel);
            changed = true;
        }

        if (resultEnchantments.keySet().stream().noneMatch(x -> x.is(Enchantments.WIND_BURST))
            && resultEnchantments.keySet().stream().anyMatch(x -> x.is(EnchantmentRegistry.SEALED_MARKER)))
        {
            var enchantmentRegistry = getEnchantmentRegistry(event.getPlayer().level());
            resultEnchantments.set(enchantmentRegistry.getOrThrow(EnchantmentRegistry.SEALED_MARKER), 0);
        }

        if (!changed || flintsConsumed == 0 || resultEnchantments.keySet().isEmpty())
        {
            event.setCanceled(true);
            return;
        }

        var result = leftItemStack.copy();
        EnchantmentHelper.setEnchantments(result, resultEnchantments.toImmutable());
        event.setOutput(result);
        event.setMaterialCost(flintsConsumed);
        event.setXpCost(flintsConsumed);
    }

    private static void moveEnchantmentsToBook(AnvilUpdateEvent event)
    {
        var leftItemStack = event.getLeft();
        var result = new ItemStack(Items.ENCHANTED_BOOK);
        var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
        var resultEnchantments = addSealedMarkerIfContainsWindBurst(enchantments, event.getPlayer().level());
        EnchantmentHelper.setEnchantments(result, resultEnchantments);
        event.setOutput(result);
        event.setXpCost(COPY_ENCHANTS_TO_BOOK_COST);
        event.setMaterialCost(1);
    }

    private static void copyEnchantedBook(AnvilUpdateEvent event, int booksToCopyAmount)
    {
        var leftItemStack = event.getLeft();
        var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);

        var resultEnchantments = addSealedMarkerIfContainsWindBurst(enchantments, event.getPlayer().level());
        ItemStack output = leftItemStack.copy();
        EnchantmentHelper.setEnchantments(output, resultEnchantments);
        output.setCount(booksToCopyAmount + 1);
        event.setOutput(output);
        event.setMaterialCost(booksToCopyAmount);

        // see GrindstoneMenu.ctor().getExperienceFromItem()
        int cost = 0;
        for (var ench : enchantments.entrySet())
        {
            var key = ench.getKey().value();
            var value = ench.getIntValue();
            if (!ench.getKey().is(PERSISTENT))
            {
                cost += key.getMinCost(value) / 17;
            }
        }

        if (cost < 1) cost = 1;
        event.setXpCost(booksToCopyAmount * cost);
    }

    private static void copyEnchantedGear(AnvilUpdateEvent event)
    {
        var leftItemStack = event.getLeft();
        var result = leftItemStack.copy();
        var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
        EnchantmentHelper.setEnchantments(result, enchantments);
        result.setCount(2);
        event.setOutput(result);
        event.setXpCost(COPY_ENCHANTS_COST);
    }

    private static void enchantGearWithBook(AnvilUpdateEvent event)
    {
        var leftItemStack = event.getLeft();
        var rightItemStack = event.getRight();

        int xpCost = getXpCost(leftItemStack, rightItemStack, AnvilMergeMode.ITEM_BOOK, leftItemStack::supportsEnchantment);
        event.setXpCost(xpCost);
    }

    private static void combineEnchantedItems(AnvilUpdateEvent event)
    {
        var leftItemStack = event.getLeft();
        var rightItemStack = event.getRight();
        var leftEnchs = EnchantmentHelper.getEnchantmentsForCrafting(leftItemStack);
        var rightEnchs = EnchantmentHelper.getEnchantmentsForCrafting(rightItemStack);

        var leftEnchMutable = new ItemEnchantments.Mutable(leftEnchs);
        boolean changed = false;

        for (var entry : rightEnchs.entrySet())
        {
            var key = entry.getKey();
            boolean isEnchantmentSupportedByItem = leftItemStack.supportsEnchantment(key);
            boolean areEnchantmentsCompatible = EnchantmentHelper.isEnchantmentCompatible(leftEnchs.keySet(), key);
            boolean areEnchantmentsAlreadyPresent = leftEnchs.keySet().contains(key);
            boolean canEnchant = isEnchantmentSupportedByItem && (areEnchantmentsCompatible || areEnchantmentsAlreadyPresent);

            if (!canEnchant) continue;

            boolean isWindBurst = key.is(Enchantments.WIND_BURST);
            int rightLevel = entry.getIntValue();
            int leftLevel = leftEnchMutable.getLevel(key);
            int finalLevel;

            if (isWindBurst && rightLevel == leftLevel && rightItemStack.is(Items.ENCHANTED_BOOK)
                    && rightEnchs.keySet().stream().noneMatch(x -> x.is(EnchantmentRegistry.SEALED_MARKER)))
            {
                finalLevel = Math.min(rightLevel + 1, key.value().getMaxLevel());
            }
            else
            {
                finalLevel = Math.max(leftLevel, rightLevel);
            }

            leftEnchMutable.set(key, finalLevel);
            if (finalLevel != leftLevel) changed = true;
        }

        var result = leftItemStack.copy();
        int resultDamageValue = getResultDamageValue(leftItemStack, rightItemStack);

        boolean durabilityChanged = leftItemStack.isDamageableItem() && rightItemStack.isDamageableItem()
                && (leftItemStack.getDamageValue() != resultDamageValue || rightItemStack.getDamageValue() != resultDamageValue);

        if (!changed && !durabilityChanged)
        {
            event.setCanceled(true);
            return;
        }

        result.setDamageValue(resultDamageValue);
        EnchantmentHelper.setEnchantments(result, leftEnchMutable.toImmutable());
        var anvilMergeMode = rightItemStack.is(Items.ENCHANTED_BOOK)? AnvilMergeMode.ITEM_BOOK : AnvilMergeMode.ITEM_ITEM;
        int xpCost = getXpCost(leftItemStack, rightItemStack, anvilMergeMode, leftItemStack::supportsEnchantment);
        event.setOutput(result);
        event.setXpCost(xpCost);
        event.setMaterialCost(1);
    }

    private static void combineEnchantedBooks(AnvilUpdateEvent event)
    {
        var leftItemStack = event.getLeft();
        var rightItemStack = event.getRight();
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

            boolean isWindBurst = key.is(Enchantments.WIND_BURST);
            int rightLevel = entry.getIntValue();
            int leftLevel = leftEnchMutable.getLevel(key);
            int finalLevel;

            if (isWindBurst && rightLevel == leftLevel
                    && rightEnchs.keySet().stream().noneMatch(x -> x.is(EnchantmentRegistry.SEALED_MARKER)))
            {
                finalLevel = Math.min(rightLevel + 1, key.value().getMaxLevel());
            }
            else
            {
                finalLevel = Math.max(leftLevel, rightLevel);
            }

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
        int xpCost = getXpCost(leftItemStack, rightItemStack, AnvilMergeMode.BOOK_BOOK, e -> true);
        event.setOutput(result);
        event.setXpCost(xpCost);
        event.setMaterialCost(1);
    }

    private static ItemEnchantments addSealedMarkerIfContainsWindBurst(ItemEnchantments enchantments, Level level)
    {
        boolean needToAddSealedMarker = enchantments.keySet().stream().anyMatch(x -> x.is(Enchantments.WIND_BURST));
        var mutable = new ItemEnchantments.Mutable(enchantments);
        if (needToAddSealedMarker)
        {
            var enchantmentRegistry = getEnchantmentRegistry(level);
            var sealedEnchantment = enchantmentRegistry.getOrThrow(EnchantmentRegistry.SEALED_MARKER);
            mutable.set(sealedEnchantment, 1);
        }

        return mutable.toImmutable();
    }

    private static @NotNull Registry<Enchantment> getEnchantmentRegistry(Level level)
    {
        var registryAccess = level.registryAccess();
        return registryAccess.lookupOrThrow(Registries.ENCHANTMENT);
    }

    private static int getXpCost(
            ItemStack left,
            ItemStack right,
            AnvilMergeMode mode,
            Predicate<Holder<Enchantment>> supportsEnchantment
    )
    {
        int cost = 0;
        boolean anyApplied = false;

        var leftEnchs = EnchantmentHelper.getEnchantmentsForCrafting(left);
        var rightEnchs = EnchantmentHelper.getEnchantmentsForCrafting(right);

        for (var entry : rightEnchs.entrySet())
        {
            var enchHolder = entry.getKey();
            var ench = enchHolder.value();

            if (enchHolder.is(EnchantmentRegistry.SEALED_MARKER)) continue;

            int leftLevel = leftEnchs.getLevel(enchHolder);
            int rightLevel = entry.getIntValue();

            boolean isNotSealedWindBurst = enchHolder.is(Enchantments.WIND_BURST)
                    && rightLevel == leftLevel
                    && rightEnchs.keySet().stream().noneMatch(x -> x.is(EnchantmentRegistry.SEALED_MARKER))
                    && leftEnchs.keySet().stream().noneMatch(x -> x.is(EnchantmentRegistry.SEALED_MARKER));

            int resultLevel =
                    isNotSealedWindBurst
                            ? rightLevel + 1
                            : Math.max(leftLevel, rightLevel);

            resultLevel = Math.min(resultLevel, ench.getMaxLevel());

            boolean compatible = true;

            // conflict with existing enchantments
            for (var existing : leftEnchs.keySet())
            {
                if (!existing.equals(enchHolder)
                        && !Enchantment.areCompatible(existing, enchHolder))
                {
                    compatible = false;
                    cost += 1; // vanilla penalty
                }
            }

            // enchantment not supported by item
            if (mode != AnvilMergeMode.BOOK_BOOK
                    && !supportsEnchantment.test(enchHolder))
            {
                compatible = false;
                cost += 1;
            }

            if (!compatible)
            {
                continue;
            }

            anyApplied = true;

            int perLevelCost = ench.getAnvilCost();

            if (mode == AnvilMergeMode.BOOK_BOOK
                    || mode == AnvilMergeMode.ITEM_BOOK)
            {
                perLevelCost = Math.max(1, perLevelCost / 2);
            }

            cost += perLevelCost * resultLevel;
        }

        // repair cost for item + item
        if (left.isDamageableItem() && right.isDamageableItem() && left.is(right.getItem()))
        {
            int leftDamage = left.getDamageValue();
            int max = left.getMaxDamage();
            int repairPerItem = Math.min(leftDamage, max / 4);
            int rightCount = right.getCount();

            for (int i = 0; i < rightCount && repairPerItem > 0; i++)
            {
                cost += 2; // vanilla adds 2 XP per repair step
            }
        }

        // vanilla repair tax for item + item
        if (mode == AnvilMergeMode.ITEM_ITEM && anyApplied)
        {
            cost += 2;
        }

        return cost;
    }

    private static int getResultDamageValue(ItemStack left, ItemStack right)
    {
        if (!left.isDamageableItem())
        {
            return left.getDamageValue();
        }

        if (!right.isDamageableItem() || !left.is(right.getItem()))
        {
            return left.getDamageValue();
        }

        int leftMax = left.getMaxDamage();
        int leftRemaining = leftMax - left.getDamageValue();
        int rightRemaining = right.getMaxDamage() - right.getDamageValue();

        int combinedRemaining = leftRemaining + rightRemaining + leftMax * 12 / 100;

        int resultDamage = leftMax - combinedRemaining;

        if (resultDamage < 0)
        {
            resultDamage = 0;
        }

        return resultDamage;
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
        event.setXpCost(REPAIR_COST * materialNumberConsumed);
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

