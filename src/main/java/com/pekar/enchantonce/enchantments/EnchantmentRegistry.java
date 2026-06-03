package com.pekar.enchantonce.enchantments;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

import static com.pekar.enchantonce.Main.MODID;
import static com.pekar.enchantonce.utils.Resources.createResourceLocation;

public class EnchantmentRegistry
{
    public static final ResourceKey<Enchantment> LOCK_MARKER =
            ResourceKey.create(Registries.ENCHANTMENT, createResourceLocation(MODID, "sealed_marker"));

    public static void initStatic()
    {
        // just to initialize the class
    }
}
