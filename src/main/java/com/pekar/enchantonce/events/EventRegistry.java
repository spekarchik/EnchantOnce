package com.pekar.enchantonce.events;

import net.minecraftforge.common.MinecraftForge;

public class EventRegistry
{
    public static void registerEvents()
    {
        register(new WorldEvents());
        register(new ConsoleCommandEvents());
    }

    private static void register(IEventHandler eventHandler)
    {
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }
}
