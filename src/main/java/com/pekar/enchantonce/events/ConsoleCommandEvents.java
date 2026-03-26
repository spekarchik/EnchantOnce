package com.pekar.enchantonce.events;

import com.mojang.logging.LogUtils;
import com.pekar.enchantonce.commands.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

public class ConsoleCommandEvents implements IEventHandler
{
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        try
        {
            var server = event.getServer();
            var commands = server.getCommands();
            var dispatcher = commands.getDispatcher();

            // Delegate registration to per-command classes
            DamageMainHandCommand.register(dispatcher);
            RepairMainHandCommand.register(dispatcher);
            DamageArmorCommand.register(dispatcher);
            RepairArmorCommand.register(dispatcher);
            FoodCommand.register(dispatcher);
            HpCommand.register(dispatcher);
            EnchantMaxCommand.register(dispatcher);
            EnchantArmorMaxCommand.register(dispatcher);
            Xp500Command.register(dispatcher);
            DayLockCommand.register(dispatcher);

            LOGGER.info("Registered console commands: damageMainHand, repairMainHand, damageArmor, repairArmor, hp, food, enchantMax, enchantArmorMax...");
        }
        catch (Throwable t)
        {
            LOGGER.warn("Could not register console commands: {}", t.toString());
        }
    }
}
