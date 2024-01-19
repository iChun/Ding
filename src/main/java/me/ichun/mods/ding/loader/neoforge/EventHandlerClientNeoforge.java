package me.ichun.mods.ding.loader.neoforge;

import me.ichun.mods.ding.common.core.EventHandlerClient;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TickEvent;

public class EventHandlerClientNeoforge extends EventHandlerClient
{
    public EventHandlerClientNeoforge()
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

    public void onWorldTick(TickEvent.LevelTickEvent event)
    {
        if(event.phase == TickEvent.Phase.END)
        {
            onWorldTickEnd();
        }
    }

    private void onClientTick(TickEvent.ClientTickEvent event)
    {
        if(event.phase == TickEvent.Phase.END)
        {
            onClientTickEnd();
        }
    }
}
