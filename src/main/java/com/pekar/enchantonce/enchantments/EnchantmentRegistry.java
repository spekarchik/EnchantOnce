package com.pekar.enchantonce.enchantments;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import static com.pekar.enchantonce.Main.MODID;

public class EnchantmentRegistry
{
    public static final ResourceKey<Enchantment> SEALED_MARKER =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(MODID, "sealed_marker"));

    public static void initStatic()
    {
        // reference the holder so IDE/compiler won't warn about it being unused
        // runtime has no effect — this forces the field to be referenced so it's kept
    }
}
