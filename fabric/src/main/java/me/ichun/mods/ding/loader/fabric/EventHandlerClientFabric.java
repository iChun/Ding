package me.ichun.mods.ding.loader.fabric;

import me.ichun.mods.ding.common.core.EventHandlerClient;
import me.ichun.mods.ichunutil.loader.fabric.event.client.FabricClientEvents;

public class EventHandlerClientFabric extends EventHandlerClient
{
    public EventHandlerClientFabric()
    {
        loaderProxy = this;

        FabricClientEvents.OVERLAY_CHANGE.register(EventHandlerClient::onOverlayChange);
    }
}
