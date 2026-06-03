package com.pekar.enchantonce.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import com.pekar.enchantonce.Main;
import com.pekar.enchantonce.utils.ItemStackWrapper;
import com.pekar.enchantonce.utils.Utils;
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

public class EnchantArmorMaxCommand
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final TagKey<Enchantment> EXCLUDED_ENCHANTMENTS = TagKey.create(Registries.ENCHANTMENT, createResourceLocation(Main.MODID, "excluded_enchantments"));
    private static final TagKey<Enchantment> EXCLUDED_FROM_BASIC_ENCHANTMENTS = TagKey.create(Registries.ENCHANTMENT, createResourceLocation(Main.MODID, "excluded_from_basic_enchantments"));
    private static final String commandName = "enchantArmorMax";
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
                .executes(ctx -> handleEnchantArmorCommand(ctx, Mode.DEFAULT))
                .then(Commands.literal("all")
                        .executes(ctx -> handleEnchantArmorCommand(ctx, Mode.ALL))
                )
                .then(Commands.literal("basic")
                        .executes(ctx -> handleEnchantArmorCommand(ctx, Mode.BASIC))
                )
                .then(Commands.literal("clear")
                        .executes(ctx -> handleEnchantArmorCommand(ctx, Mode.CLEAR))
                )
        );

        LOGGER.debug("EnchantArmorMaxCommand registered");
    }

    private static int handleEnchantArmorCommand(CommandContext<CommandSourceStack> ctx, Mode mode)
    {
        try
        {
            Player player = ctx.getSource().getPlayerOrException();
            var armorStacks = Utils.instance.player.getArmorInSlots(player);

            Registry<Enchantment> registry =
                    player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);

            String[] slotNames = {"head", "chest", "legs", "feet"};
            StringBuilder sb = new StringBuilder();
            boolean anyChanged = false;

            for (int i = 0; i < armorStacks.size(); i++)
            {
                ItemStack stack = armorStacks.get(i);
                if (stack == null || stack.isEmpty())
                {
                    sb.append(slotNames[i]).append(": empty; ");
                    continue;
                }

                if (!stack.isEnchantable() && !stack.isEnchanted())
                {
                    sb.append(slotNames[i]).append(": not enchantable; ");
                    continue;
                }

                Map<Enchantment, Integer> mutableEnchantments = new HashMap<>();
                for (var enchantment : registry.asLookup().listElements().toList())
                {
                    int level = enchantment.value().getMaxLevel();
                    if (enchantment.value().isCurse() || mode == Mode.CLEAR) level = 0;

                    if (mode != Mode.ALL)
                    {
                        if (enchantment.is(EXCLUDED_ENCHANTMENTS)) level = 0;
                        if (mode == Mode.BASIC && enchantment.is(EXCLUDED_FROM_BASIC_ENCHANTMENTS)) level = 0;
                        boolean isExclusive = mutableEnchantments.keySet().stream().anyMatch(x -> !x.isCompatibleWith(enchantment.value()));
                        if (isExclusive) level = 0;
                    }

                    if (ItemStackWrapper.of(stack).supportsEnchantment(enchantment) || mode == Mode.CLEAR)
                    {
                        mutableEnchantments.put(enchantment.value(), level);
                    }
                }

                EnchantmentHelper.setEnchantments(mutableEnchantments, stack);
                anyChanged = true;
                var commandResult = switch (mode)
                {
                    case ALL -> "applied all";
                    case BASIC -> "applied basic";
                    case CLEAR -> "cleared";
                    default -> "applied";
                };
                sb.append(slotNames[i]).append(": ").append(commandResult).append("; ");
            }

            if (anyChanged)
            {
                ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": " + sb), false);
                return 1;
            }
            else
            {
                ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": no enchantable armor found or nothing changed"), false);
                return 0;
            }
        }
        catch (Exception e)
        {
            ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": this command must be run by a player"), false);
            return 0;
        }
    }
}