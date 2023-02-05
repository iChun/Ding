package me.ichun.mods.ding.loader.forge;

import me.ichun.mods.ding.common.core.EventHandlerClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.registries.ForgeRegistries;

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

    @Override
    public void hookIntoClientTick()
    {
        MinecraftForge.EVENT_BUS.addListener(this::onClientTick);
    }

    @Override
    public SoundEvent getSoundEvent(ResourceLocation rl)
    {
        return ForgeRegistries.SOUND_EVENTS.getValue(rl);
    }

    public void onClientLoggedInEvent(ClientPlayerNetworkEvent.LoggedOutEvent event)
    {
        promptToPlayWorld();
    }

    public void onWorldTick(TickEvent.WorldTickEvent event)
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
