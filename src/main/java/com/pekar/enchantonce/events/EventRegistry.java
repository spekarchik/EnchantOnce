package com.pekar.enchantonce.events;

import net.neoforged.neoforge.common.NeoForge;

public class EventRegistry
{
    public static void registerEvents()
    {
        register(new AnvilEvents());
        register(new ConsoleCommandEvents());
    }

    private static void register(IEventHandler eventHandler)
    {
        NeoForge.EVENT_BUS.register(eventHandler);
    }
}
