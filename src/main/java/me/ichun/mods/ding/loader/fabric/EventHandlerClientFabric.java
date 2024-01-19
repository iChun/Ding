package me.ichun.mods.ding.loader.fabric;

import me.ichun.mods.ding.common.core.EventHandlerClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
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
        ClientLoginConnectionEvents.INIT.register(this::onClientConnection);
        ClientTickEvents.END_WORLD_TICK.register(this::onWorldTick);
    }

    @Override
    public void hookIntoClientTick()
    {
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
    }

    public void onClientConnection(ClientHandshakePacketListenerImpl handler, Minecraft client)
    {
        promptToPlayWorld();
    }

    public void onWorldTick(ClientLevel level)
    {
        onWorldTickEnd();
    }

    public void onClientTick(Minecraft client)
    {
        onClientTickEnd();
    }
}
