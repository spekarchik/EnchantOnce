package com.pekar.enchantonce.events.handlers;

import com.pekar.enchantonce.Config;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Predicate;

public class AnvilHelper
{
    public static @NotNull Registry<Enchantment> getEnchantmentRegistry(Level level)
    {
        var registryAccess = level.registryAccess();
        return registryAccess.registryOrThrow(Registries.ENCHANTMENT);
    }

    public static void setHistoryWeightToResult(ItemStack leftItemStack, ItemStack rightItemStack, ItemStack result, boolean increaseWeight)
    {
        int leftRepair = leftItemStack.getBaseRepairCost();
        int rightRepair = rightItemStack.getBaseRepairCost();
        int newRepairCost = Math.max(leftRepair, rightRepair);
        if (increaseWeight)
            newRepairCost = AnvilMenu.calculateIncreasedRepairCost(newRepairCost);

        result.setRepairCost(newRepairCost);
    }

    public static int getXpCost(
            ItemStack left,
            ItemStack right,
            AnvilMergeMode mode,
            Predicate<Enchantment> supportsEnchantment
    )
    {
        int cost = 0;
        boolean anyApplied = false;

        var leftEnchs = EnchantmentHelper.getEnchantments(left);
        var rightEnchs = EnchantmentHelper.getEnchantments(right);

        // 1. prior work cost
        int priorWork = left.getBaseRepairCost()
                + right.getBaseRepairCost();
        cost += priorWork;

        for (var entry : rightEnchs.entrySet())
        {
            var rightEnch = entry.getKey();

            int leftLevel = leftEnchs.getOrDefault(rightEnch, 0);
            int rightLevel = entry.getValue();

            int resultLevel = Math.max(leftLevel, rightLevel);

            resultLevel = Math.min(resultLevel, rightEnch.getMaxLevel());

            boolean compatible = true;

            // conflict with existing enchantments
            for (var existing : leftEnchs.keySet())
            {
                if (!existing.equals(rightEnch) && !existing.isCompatibleWith(rightEnch))
                {
                    compatible = false;
                    cost += 1; // vanilla penalty
                }
            }

            // enchantment not supported by item
            if (mode != AnvilMergeMode.BOOK_BOOK
                    && !supportsEnchantment.test(rightEnch))
            {
                compatible = false;
            }

            if (!compatible) continue;

            anyApplied = true;

            int perLevelCost;
            switch (rightEnch.getRarity())
            {
                case COMMON -> perLevelCost = 1;
                case UNCOMMON -> perLevelCost = 2;
                case RARE -> perLevelCost = 4;
                case VERY_RARE -> perLevelCost = 8;
                default -> perLevelCost = 0;
            }

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

        // clamp like vanilla unless high anvil costs are explicitly allowed
        if (!Config.ALLOW_HIGH_ANVIL_COST.get() && cost > 40) cost = 40;

        return cost;
    }

    public static void cleanEnchantmentsExceptCurses(ItemStack item)
    {
        var enchantments = EnchantmentHelper.getEnchantments(item);
        var resultEnchantments = new HashMap<>(enchantments);

        for (var entry : enchantments.entrySet())
        {
            var key = entry.getKey();
            if (key.isCurse()) continue;

            resultEnchantments.remove(key);
        }

        EnchantmentHelper.setEnchantments(resultEnchantments, item);
    }
}
