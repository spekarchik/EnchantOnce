package com.pekar.enchantonce;

import com.mojang.logging.LogUtils;
import com.pekar.enchantonce.events.EventRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main
{
    // Directly reference a log4j logger.
    public static final String MODID = "enchantonce";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Main()
    {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        EventRegistry.registerEvents();
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    //@SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
//        LOGGER.info("HELLO from server starting");
    }
}
