package com.pekar.enchantonce.events.handlers;

import com.pekar.enchantonce.enchantments.EnchantmentRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

class AnvilHelper
{
    public static ItemEnchantments addLockMarkerIfContainsWindBurst(ItemEnchantments enchantments, Level level)
    {
        boolean needToAddSealedMarker = enchantments.keySet().stream().anyMatch(x -> x.is(Enchantments.WIND_BURST));
        var mutable = new ItemEnchantments.Mutable(enchantments);
        if (needToAddSealedMarker)
        {
            var enchantmentRegistry = getEnchantmentRegistry(level);
            var sealedEnchantment = enchantmentRegistry.getOrThrow(EnchantmentRegistry.LOCK_MARKER);
            mutable.set(sealedEnchantment, 1);
        }

        return mutable.toImmutable();
    }

    public static @NotNull Registry<Enchantment> getEnchantmentRegistry(Level level)
    {
        var registryAccess = level.registryAccess();
        return registryAccess.lookupOrThrow(Registries.ENCHANTMENT);
    }

    public static void setHistoryWeightToResult(ItemStack leftItemStack, ItemStack rightItemStack, ItemStack result, boolean increaseWeight)
    {
        int leftRepair = leftItemStack.getOrDefault(DataComponents.REPAIR_COST, 0);
        int rightRepair = rightItemStack.getOrDefault(DataComponents.REPAIR_COST, 0);
        int newRepairCost = Math.max(leftRepair, rightRepair);
        if (increaseWeight)
            newRepairCost = AnvilMenu.calculateIncreasedRepairCost(newRepairCost);

        result.set(DataComponents.REPAIR_COST, newRepairCost);
    }

    public static int getXpCost(
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

        // 1. prior work cost
        long priorWork = (long) left.getOrDefault(DataComponents.REPAIR_COST, 0)
                + (long) right.getOrDefault(DataComponents.REPAIR_COST, 0);
        cost += priorWork;

        for (var entry : rightEnchs.entrySet())
        {
            var enchHolder = entry.getKey();
            var ench = enchHolder.value();

            if (enchHolder.is(EnchantmentRegistry.LOCK_MARKER)) continue;

            int leftLevel = leftEnchs.getLevel(enchHolder);
            int rightLevel = entry.getIntValue();

            boolean isNotSealedWindBurst = enchHolder.is(Enchantments.WIND_BURST)
                    && rightLevel == leftLevel
                    && rightEnchs.keySet().stream().noneMatch(x -> x.is(EnchantmentRegistry.LOCK_MARKER))
                    && leftEnchs.keySet().stream().noneMatch(x -> x.is(EnchantmentRegistry.LOCK_MARKER));

            int resultLevel = isNotSealedWindBurst
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

            if (!compatible) continue;

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

        // renaming cost
        boolean renamed = left.has(DataComponents.CUSTOM_NAME) || right.has(DataComponents.CUSTOM_NAME);
        if (renamed)
        {
            cost += 1;
        }

        // clamp like vanilla
        if (cost > 40) cost = 40;

        return cost;
    }
}
