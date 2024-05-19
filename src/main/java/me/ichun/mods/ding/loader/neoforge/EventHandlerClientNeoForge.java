package me.ichun.mods.ding.loader.neoforge;

import me.ichun.mods.ding.common.core.EventHandlerClient;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
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
        NeoForge.EVENT_BUS.addListener(this::onClientLoggedInEvent);
        NeoForge.EVENT_BUS.addListener(this::onWorldTick);
    }

    @Override
    public void hookIntoClientTick()
    {
        NeoForge.EVENT_BUS.addListener(this::onClientTick);
    }

    public void onClientLoggedInEvent(ClientPlayerNetworkEvent.LoggingIn event)
    {
        promptToPlayWorld();
    }

    public void onWorldTick(LevelTickEvent.Post event)
    {
        onWorldTickEnd();
    }

    private void onClientTick(ClientTickEvent.Post event)
    {
        onClientTickEnd();
    }
}
