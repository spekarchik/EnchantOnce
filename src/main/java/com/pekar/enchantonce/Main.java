package com.pekar.enchantonce;

import com.mojang.logging.LogUtils;
import com.pekar.enchantonce.enchantments.EnchantmentRegistry;
import com.pekar.enchantonce.events.EventRegistry;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
//@Mod(Main.MODID)
public class Main
{
    public static final String MODID = "enchantonce";
    private static final Logger LOGGER = LogUtils.getLogger();

//    public Main(IEventBus modEventBus, ModContainer modContainer)
//    {
//        initializeRegistry();
//
//        NeoForge.EVENT_BUS.register(this);
//        EventRegistry.registerEvents();
//
//        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
//    }
//
//    private void initializeRegistry()
//    {
//        EnchantmentRegistry.initStatic();
//    }
//
//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event)
//    {
//    }
}
