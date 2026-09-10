package com.pekar.enchantonce;

import com.pekar.enchantonce.config.ConfigSyncPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.Map;

public class ClientMain implements ClientModInitializer
{
    private Map<String, String> localSettings;

    @Override
    public void onInitializeClient()
    {
        ClientPlayNetworking.registerGlobalReceiver(ConfigSyncPayload.TYPE, (payload, context) -> {
            // The integrated server shares these config objects with its local client.
            if (context.client().getSingleplayerServer() != null)
            {
                return;
            }
            var previous = Config.SPEC.snapshot();
            try
            {
                Config.SPEC.applySnapshot(payload.values());
                if (localSettings == null)
                {
                    localSettings = previous;
                }
            }
            catch (IllegalArgumentException e)
            {
                Main.LOGGER.warn("Cannot apply server config; EnchantOnce config versions may differ", e);
            }
        });

        ClientPlayConnectionEvents.DISCONNECT.register((listener, client) -> {
            if (localSettings != null)
            {
                Config.SPEC.applySnapshot(localSettings);
                localSettings = null;
            }
        });
    }
}
