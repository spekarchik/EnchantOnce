package com.pekar.enchantonce.events.handlers.base;

import com.pekar.enchantonce.events.AnvilUpdateEvent;
import com.pekar.enchantonce.events.handlers.update.AnvilUpdateEventWrapper;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import static com.pekar.enchantonce.Main.MODID;
import static com.pekar.enchantonce.utils.Resources.createResourceLocation;

public abstract class AnvilUpdateEventHandler extends AnvilEventHandler<AnvilUpdateEvent>
{
    protected ItemStack leftItemStack;
    protected ItemStack rightItemStack;
    protected AnvilUpdateEventWrapper event;
    protected static final TagKey<Enchantment> PERSISTENT = TagKey.create(Registries.ENCHANTMENT, createResourceLocation(MODID, "persistent"));

    @Override
    public boolean tryHandle(AnvilUpdateEvent event)
    {
        this.event = new AnvilUpdateEventWrapper(event);
        rightItemStack = event.getRight();
        leftItemStack = event.getLeft();

        return super.tryHandle(event);
    }
}
