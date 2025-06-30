package com.pekar.enchantonce;

import com.mojang.logging.LogUtils;
import com.pekar.enchantonce.events.EventRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main
{
    public static final String MODID = "enchantonce";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Main(IEventBus modEventBus, ModContainer modContainer)
    {
        initializeRegistry();

        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);
        EventRegistry.registerEvents();
    }

    private void initializeRegistry()
    {
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        //LOGGER.info("HELLO from server starting");
    }
}
