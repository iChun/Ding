package me.ichun.mods.ding.loader.forge;

import me.ichun.mods.ding.common.core.EventHandlerClient;
import me.ichun.mods.ichunutil.loader.forge.event.client.OverlayChangeEvent;
import net.minecraftforge.common.MinecraftForge;

public class EventHandlerClientForge extends EventHandlerClient
{
    public EventHandlerClientForge()
    {
        loaderProxy = this;

        MinecraftForge.EVENT_BUS.addListener(this::onOverlayChangeEvent);
    }

    private void onOverlayChangeEvent(OverlayChangeEvent event)
    {
        onOverlayChange(event.getCurrentOverlay(), event.getNewOverlay());
    }
}
