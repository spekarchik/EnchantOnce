package com.pekar.enchantonce.utils;

import java.util.Random;

public class Utils
{
    public Utils()
    {}

    public static final int TICKS_PER_SECOND = 20;

    public static final Utils instance = new Utils();

    public static Random random = new Random();

    public final Player player = new Player();
}
