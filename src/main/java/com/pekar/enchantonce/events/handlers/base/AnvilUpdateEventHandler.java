package com.pekar.enchantonce.events.handlers.base;

import com.pekar.enchantonce.events.handlers.AnvilUpdateEventWrapper;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

import static com.pekar.enchantonce.Main.MODID;
import static com.pekar.enchantonce.utils.Resources.createResourceLocation;

public abstract class AnvilUpdateEventHandler
{
    AnvilUpdateEventHandler first;
    private AnvilUpdateEventHandler next;
    protected ItemStack leftItemStack;
    protected ItemStack rightItemStack;
    protected AnvilUpdateEventWrapper event;
    protected static final TagKey<Enchantment> PERSISTENT = TagKey.create(Registries.ENCHANTMENT, createResourceLocation(MODID, "persistent"));

    public final AnvilUpdateEventHandler asFirst()
    {
        first = this;
        return this;
    }

    public final AnvilUpdateEventHandler getFirst()
    {
        return first;
    }

    public final AnvilUpdateEventHandler attach(AnvilUpdateEventHandler next)
    {
        this.next = next;
        this.next.first = first;
        return next;
    }

    public final boolean tryHandle(AnvilUpdateEvent event)
    {
        this.event = new AnvilUpdateEventWrapper(event);
        rightItemStack = event.getRight();
        leftItemStack = event.getLeft();

        return handleInternally() || (next != null && next.tryHandle(event));
    }

    protected abstract boolean handleInternally();
}
