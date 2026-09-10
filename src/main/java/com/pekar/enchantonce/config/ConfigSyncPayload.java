package com.pekar.enchantonce.config;

import com.pekar.enchantonce.Main;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;

public record ConfigSyncPayload(Map<String, String> values) implements FabricPacket
{
    public static final PacketType<ConfigSyncPayload> TYPE = PacketType.create(
            new ResourceLocation(Main.MODID, "config_sync_v1"), ConfigSyncPayload::read);

    public ConfigSyncPayload
    {
        values = Map.copyOf(values);
    }

    private static ConfigSyncPayload read(FriendlyByteBuf buffer)
    {
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
    }

    @Override
    public void write(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(values.size());
        values.forEach((name, value) -> {
            buffer.writeUtf(name, 128);
            buffer.writeUtf(value, 32);
        });
    }

    @Override
    public PacketType<ConfigSyncPayload> getType()
    {
        return TYPE;
    }
}
