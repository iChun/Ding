package me.ichun.mods.ding.loader.fabric;

import me.ichun.mods.ding.common.core.EventHandlerClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

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

    @Override
    public SoundEvent getSoundEvent(ResourceLocation rl)
    {
        return BuiltInRegistries.SOUND_EVENT.get(rl);
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
