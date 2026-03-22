package com.pekar.enchantonce.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

import java.util.Random;

public class Utils
{
    public Utils()
    {}

    public static final int TICKS_PER_SECOND = 20;

    public static final Utils instance = new Utils();

    public static Random random = new Random();

    public final Player player = new Player();
    public final Resources resources = new Resources();

    public AABB getRenderBoundingBox(BlockPos pos)
    {
        return new AABB(pos);
    }
}
