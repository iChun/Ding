package me.ichun.mods.ding.loader.forge;

import me.ichun.mods.ding.common.core.EventHandlerClient;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

public class EventHandlerClientForge extends EventHandlerClient
{
    public EventHandlerClientForge()
    {
        loaderProxy = this;
    }

    @Override
    public void hookIntoWorldTick()
    {
        MinecraftForge.EVENT_BUS.addListener(this::onClientLoggedInEvent);
        MinecraftForge.EVENT_BUS.addListener(this::onWorldTick);
    }

    public void onClientLoggedInEvent(ClientPlayerNetworkEvent.LoggingIn event)
    {
        promptToPlayWorld();
    }

    public void onWorldTick(TickEvent.LevelTickEvent.Post event)
    {
        onWorldTickEnd();
    }
}
