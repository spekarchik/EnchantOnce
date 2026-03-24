package com.pekar.enchantonce.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Player
{
    Player()
    {

    }

    public Set<ArmorType> getArmorTypes()
    {
        return Set.of(ArmorType.HELMET, ArmorType.CHESTPLATE, ArmorType.LEGGINGS, ArmorType.BOOTS);
    }

    public List<ItemStack> getArmorInSlots(LivingEntity entity)
    {
        return Arrays.asList(
                entity.getItemBySlot(EquipmentSlot.HEAD),
                entity.getItemBySlot(EquipmentSlot.CHEST),
                entity.getItemBySlot(EquipmentSlot.LEGS),
                entity.getItemBySlot(EquipmentSlot.FEET)
        );
    }

    public List<EquipmentSlot> getArmorSlots()
    {
        return Arrays.asList(
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        );
    }

    public static boolean hasSilkTouch(ItemStack stack, ServerLevel level)
    {
        return level.getServer().registryAccess()
                .lookup(Registries.ENCHANTMENT)
                .flatMap(lookup -> lookup.get(Enchantments.SILK_TOUCH))
                .map(holder -> stack.getEnchantmentLevel(holder) > 0)
                .orElse(false);
    }
}
