package com.pekar.enchantonce;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ALLOW_BOOK_COPYING = BUILDER
            .comment("Allow copying enchanted books to create duplicates")
            .define("allowBookCopying", true);

    public static final ModConfigSpec.BooleanValue ALLOW_GEAR_COPYING = BUILDER
            .comment("Allow copying enchantments directly from one gear item to another")
            .define("allowGearCopying", true);

    public static final ModConfigSpec.BooleanValue ALLOW_MOVE_ENCHANTMENTS_TO_BOOK = BUILDER
            .comment("Allow extracting enchantments from gear and storing them in books")
            .define("allowMoveEnchantmentsToBook", true);

    public static final ModConfigSpec.BooleanValue ALLOW_FIXED_REPAIR_COST = BUILDER
            .comment("Set a fixed XP cost (2 levels) for repairing items with materials, ignoring prior work penalty")
            .define("allowFixedRepairCost", true);

    public static final ModConfigSpec.BooleanValue PREVENT_INCREASE_ENCHANTMENT_LEVEL = BUILDER
            .comment("Prevent combining items from increasing enchantment levels beyond original values")
            .define("preventIncreaseEnchantmentLevel", true);

    public static final ModConfigSpec.BooleanValue ALLOW_NONSTANDARD_REPAIRS = BUILDER
            .comment("Allow repairing items that normally cannot be repaired with materials (e.g. trident, bow, shears)")
            .define("allowNonstandardRepairs", true);

    public static final ModConfigSpec.BooleanValue ALLOW_DECREASE_ENCHANTMENT_LEVEL = BUILDER
            .comment("Allow decreasing enchantment level on books using flint")
            .define("allowDecreaseEnchantmentLevel", true);

    public static final ModConfigSpec.IntValue FIXED_REPAIR_COST = BUILDER
            .comment("XP cost (in levels) for repairing items with materials when fixed repair cost is enabled")
            .defineInRange("fixedRepairCost", 2, 1, 100);

    public static final ModConfigSpec.IntValue MOVE_ENCHANTMENTS_TO_BOOK_COST = BUILDER
            .comment("XP cost (in levels) for moving enchantments from gear to book")
            .defineInRange("moveEnchantmentsToBookCost", 1, 1, 100);

    public static final ModConfigSpec.IntValue DECREASE_ENCHANTMENT_LEVEL_COST = BUILDER
            .comment("XP cost (in levels) for decreasing enchantment level on books using flint")
            .defineInRange("decreaseEnchantmentLevelCost", 1, 1, 100);

    public static final ModConfigSpec.IntValue GEAR_COPYING_COST = BUILDER
            .comment("XP cost (in levels) for copying enchantments directly from one gear item to another")
            .defineInRange("gearCopyingCost", 25, 1, 100);

    public static final ModConfigSpec.IntValue MAX_BOOK_COPIES = BUILDER
            .comment("Maximum number of copies that can be made from an enchanted book per copy operation when book copying is enabled")
            .defineInRange("maxBookCopies", 4, 1, 100);

    static final ModConfigSpec SPEC = BUILDER.build();
}
