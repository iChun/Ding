package me.ichun.mods.ding.loader.neoforge;

import me.ichun.mods.ding.common.core.EventHandlerClient;
import me.ichun.mods.ichunutil.loader.neoforge.event.client.OverlayChangeEvent;
import net.neoforged.neoforge.common.NeoForge;

public class EventHandlerClientNeoForge extends EventHandlerClient
{
    public EventHandlerClientNeoForge()
    {
        loaderProxy = this;

        NeoForge.EVENT_BUS.addListener(this::onOverlayChangeEvent);
    }

    private void onOverlayChangeEvent(OverlayChangeEvent event)
    {
        onOverlayChange(event.getCurrentOverlay(), event.getNewOverlay());
    }
}
