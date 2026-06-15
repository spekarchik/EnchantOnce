package com.pekar.enchantonce.events.handlers.base;

import com.pekar.enchantonce.events.Event;

public abstract class AnvilEventHandler<T extends Event>
{
    protected AnvilEventHandler<T> next;
    protected AnvilEventHandler<T> first;

    public final AnvilEventHandler<T> asFirst()
    {
        first = this;
        return this;
    }

    public final AnvilEventHandler<T> getFirst()
    {
        return first;
    }

    public final AnvilEventHandler<T> attach(AnvilEventHandler<T> next)
    {
        this.next = next;
        this.next.first = first;
        return next;
    }

    public boolean tryHandle(T event)
    {
        return handleInternally() || (next != null && next.tryHandle(event));
    }

    protected abstract boolean handleInternally();
}
