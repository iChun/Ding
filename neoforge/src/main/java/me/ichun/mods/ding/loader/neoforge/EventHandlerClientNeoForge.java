package me.ichun.mods.ding.loader.neoforge;

import me.ichun.mods.ding.common.core.EventHandlerClient;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

public class EventHandlerClientNeoForge extends EventHandlerClient
{
    public EventHandlerClientNeoForge()
    {
        loaderProxy = this;
    }

    @Override
    public void hookIntoWorldTick()
    {
        NeoForge.EVENT_BUS.addListener(this::onWorldTick);
    }

    public void onWorldTick(LevelTickEvent.Post event)
    {
        onWorldTickEnd();
    }
}
