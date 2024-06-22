package me.ichun.mods.ding.loader.fabric;

import me.ichun.mods.ding.common.core.EventHandlerClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.multiplayer.ClientLevel;

public class EventHandlerClientFabric extends EventHandlerClient
{
    public EventHandlerClientFabric()
    {
        loaderProxy = this;
    }

    @Override
    public void hookIntoWorldTick()
    {
        ClientTickEvents.END_WORLD_TICK.register(this::onWorldTick);
    }

    public void onWorldTick(ClientLevel level)
    {
        onWorldTickEnd();
    }
}
