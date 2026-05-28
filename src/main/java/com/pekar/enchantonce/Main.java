package com.pekar.enchantonce;

import com.pekar.enchantonce.commands.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main implements ModInitializer
{
	public static final String MODID = "enchantonce";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize()
	{
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		var configPath = FabricLoader.getInstance()
				.getConfigDir()
				.resolve("enchantonce-common.toml");

		try
		{
			Config.SPEC.load(configPath);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Failed to load config", e);
		}

		//LOGGER.info("Hello Fabric world!");
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

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
		});
	}
}