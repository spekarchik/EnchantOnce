package com.pekar.enchantonce.events;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

            dispatcher.register(Commands.literal("damageMainHandGear")
                    .requires(src -> {
                        try
                        {
                            // If the source is a player, allow only operators (OP)
                            var player = src.getPlayerOrException();
                            var srv = src.getServer();
                            return srv.getPlayerList().isOp(player.getGameProfile());
                        }
                        catch (Exception ex)
                        {
                            // Source is not a player (console) — allow
                            return true;
                        }
                    })
                    // no-arg: default behaviour (maxDamage - 1)
                    .executes(ctx -> handleDamageGearCommand(ctx, -1))
                    // optional int argument 'damage' (>=0)
                    .then(Commands.argument("damage", IntegerArgumentType.integer(0))
                            .executes(ctx -> handleDamageGearCommand(ctx, IntegerArgumentType.getInteger(ctx, "damage")))
                    )
            );

            LOGGER.info("Registered console command 'damageMainHandGear'");
        }
        catch (Throwable t)
        {
            LOGGER.warn("Could not register 'damageMainHandGear' command: {}", t.toString());
        }
    }

    private static int handleDamageGearCommand(CommandContext<CommandSourceStack> ctx, int requestedDamage)
    {
        try
        {
            Player player = ctx.getSource().getPlayerOrException();
            ItemStack mainHand = player.getMainHandItem();
            if (mainHand.isDamageableItem())
            {
                int max = mainHand.getMaxDamage();
                int maxAllowed = Math.max(0, max - 1);
                int finalDamage;
                if (requestedDamage < 0)
                {
                    finalDamage = maxAllowed;
                }
                else
                {
                    // clamp to [0, maxAllowed]
                    finalDamage = Math.min(Math.max(0, requestedDamage), maxAllowed);
                }
                mainHand.setDamageValue(finalDamage);
                ctx.getSource().sendSuccess(() -> Component.literal("damageMainHandGear: set main-hand item damage to " + finalDamage), false);
                return 1;
            }
            else
            {
                ctx.getSource().sendSuccess(() -> Component.literal("damageMainHandGear: you must hold a damageable item in main hand"), false);
                return 0;
            }
        }
        catch (Exception ex)
        {
            ctx.getSource().sendSuccess(() -> Component.literal("damageMainHandGear: this command must be run by a player"), false);
            return 0;
        }
    }
}
