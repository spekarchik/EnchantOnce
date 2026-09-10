package com.pekar.enchantonce.config;

import com.pekar.enchantonce.Main;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public record ConfigSyncPayload(Map<String, String> values) implements CustomPacketPayload
{
    public static final Type<ConfigSyncPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Main.MODID, "config_sync_v1"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ConfigSyncPayload> CODEC = StreamCodec.of(
            (buffer, payload) -> {
                buffer.writeVarInt(payload.values.size());
                payload.values.forEach((name, value) -> {
                    buffer.writeUtf(name, 128);
                    buffer.writeUtf(value, 32);
                });
            },
            buffer -> {
                int count = buffer.readVarInt();
                if (count < 0 || count > 64)
                {
                    throw new IllegalArgumentException("Invalid config option count");
                }
                Map<String, String> values = new LinkedHashMap<>();
                for (int i = 0; i < count; i++)
                {
                    if (values.put(buffer.readUtf(128), buffer.readUtf(32)) != null)
                    {
                        throw new IllegalArgumentException("Duplicate config option");
                    }
                }
                return new ConfigSyncPayload(values);
            });

    public ConfigSyncPayload
    {
        values = Map.copyOf(values);
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
