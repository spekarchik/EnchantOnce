package com.pekar.enchantonce.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import com.pekar.enchantonce.Main;
import com.pekar.enchantonce.utils.ItemStackWrapper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.pekar.enchantonce.utils.Resources.createResourceLocation;

public class EnchantMaxCommand
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final TagKey<Enchantment> EXCLUDED_ENCHANTMENTS = TagKey.create(Registries.ENCHANTMENT, createResourceLocation(Main.MODID, "excluded_enchantments"));
    private static final TagKey<Enchantment> EXCLUDED_FROM_BASIC_ENCHANTMENTS = TagKey.create(Registries.ENCHANTMENT, createResourceLocation(Main.MODID, "excluded_from_basic_enchantments"));
    private static final String commandName = "enchantMax";
    private enum Mode { DEFAULT, ALL, BASIC, CLEAR }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        if (dispatcher.getRoot().getChild(commandName) != null)
        {
            LOGGER.warn("Command '" + commandName + "' already exists, skipping registration");
            return;
        }

        dispatcher.register(Commands.literal(commandName)
                .requires(src -> src.hasPermission(Permissions.COMMANDS_ADMIN))
                .executes(ctx -> handleEnchantMaxCommand(ctx, Mode.DEFAULT))
                .then(Commands.literal("all")
                        .executes(ctx -> handleEnchantMaxCommand(ctx, Mode.ALL))
                )
                .then(Commands.literal("basic")
                        .executes(ctx -> handleEnchantMaxCommand(ctx, Mode.BASIC))
                )
                .then(Commands.literal("clear")
                        .executes(ctx -> handleEnchantMaxCommand(ctx, Mode.CLEAR))
                )
        );

        LOGGER.debug("EnchantMaxCommand registered");
    }

    private static int handleEnchantMaxCommand(CommandContext<CommandSourceStack> ctx, Mode mode)
    {
        try
        {
            Player player = ctx.getSource().getPlayerOrException();
            ItemStack stack = player.getMainHandItem();

            if (stack.isEmpty() || (!stack.isEnchantable() && !stack.isEnchanted()))
            {
                String message = "An enchantable item required in main hand";
                ctx.getSource().sendSuccess(() -> Component.literal(message), false);
                return 0;
            }

            Registry<Enchantment> registry =
                    player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);

            Map<Enchantment, Integer> mutableEnchantments = new HashMap<>();
            for (var enchantment : registry.asLookup().listElements().toList())
            {
                int level = enchantment.value().getMaxLevel();
                if (enchantment.value().isCurse() || mode == Mode.CLEAR) level = 0;

                // Skip Frost Walker and Silk Touch unless the 'all' literal was provided
                if (mode != Mode.ALL)
                {
                    if (enchantment.is(EXCLUDED_ENCHANTMENTS)) level = 0;
                    if (mode == Mode.BASIC && enchantment.is(EXCLUDED_FROM_BASIC_ENCHANTMENTS)) level = 0;
                    boolean isExclusive = mutableEnchantments.keySet().stream().anyMatch(x -> !x.isCompatibleWith(enchantment.value()));
                    if (isExclusive) level = 0;
                }

                if (ItemStackWrapper.of(stack).supportsEnchantment(enchantment) || mode == Mode.CLEAR)
                {
                    if (level > 0)
                        mutableEnchantments.put(enchantment.value(), level);
                    else
                        mutableEnchantments.remove(enchantment.value());
                }
            }

            EnchantmentHelper.setEnchantments(mutableEnchantments, stack);

            var commandResult = switch (mode)
            {
                case ALL -> "Applied max enchantments (including exclusive ones)";
                case BASIC -> "Applied basic enchantments";
                case CLEAR -> "Cleared all enchantments";
                default -> "Applied max enchantments";
            };
            ctx.getSource().sendSuccess(
                    () -> Component.literal(commandResult),
                    false
            );

            return 1;
        }
        catch (Exception e)
        {
            ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": this command must be run by a player"), false);
            return 0;
        }
    }
}