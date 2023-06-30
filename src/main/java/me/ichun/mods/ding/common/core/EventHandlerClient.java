package me.ichun.mods.ding.common.core;

import me.ichun.mods.ding.common.Ding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public abstract class EventHandlerClient
{
    //This is here instead of normally being in the mod class because it is only classloaded when called by the client.
    public static EventHandlerClient loaderProxy;

    public static boolean postInit;

    private static boolean played;
    private static boolean playWorld;
    private static boolean hasLoadingGui;

    public abstract void hookIntoWorldTick();
    public abstract void hookIntoClientTick();

    public abstract SoundEvent getSoundEvent(ResourceLocation rl);

    public static void postInit()
    {
        postInit = true;

        if(!played)
        {
            played = true;
            if(Ding.config.playOnLoad.get())
            {
                playSound(Ding.config.name.get(), Ding.config.volume.get().floatValue(), Ding.config.pitch.get().floatValue(), Ding.config.category.get());
            }
        }

        if(Ding.config.playOnWorld.get())
        {
            loaderProxy.hookIntoWorldTick();
        }

        if(Ding.config.playOnResourcesReload.get())
        {
            loaderProxy.hookIntoClientTick();
        }
    }

    public static void promptToPlayWorld()
    {
        playWorld = true;
    }

    public static void onWorldTickEnd()
    {
        if(playWorld && Minecraft.getInstance().player != null && (Minecraft.getInstance().player.tickCount > 20 || Minecraft.getInstance().isPaused()))
        {
            playWorld = false;
            if(Ding.config.playOnWorld.get())
            {
                playSound(Ding.config.nameWorld.get(), Ding.config.volumeWorld.get().floatValue(), Ding.config.pitchWorld.get().floatValue(), Ding.config.categoryWorld.get());
            }
        }
    }

    public static void onClientTickEnd()
    {
        if(Ding.config.playOnResourcesReload.get())
        {
            if(Minecraft.getInstance().getOverlay() == null && hasLoadingGui)
            {
                playSound(Ding.config.nameResourcesReload.get(), Ding.config.volumeResourcesReload.get().floatValue(), Ding.config.pitchResourcesReload.get().floatValue(), Ding.config.categoryResourcesReload.get());
            }
            hasLoadingGui = Minecraft.getInstance().getOverlay() instanceof LoadingOverlay;
        }
    }

    public static void playSound(String name, float volume, float pitch, String categoryName)
    {
        ResourceLocation rl = new ResourceLocation(name);
        SoundEvent sound = loaderProxy.getSoundEvent(rl);
        SoundSource category = getCategoryByName(categoryName);

        //if the sound doesn't exist we play a missing sound
        Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(sound == null ? rl : sound.getLocation(), category, volume, pitch, SoundInstance.createUnseededRandom(), false, 0, SoundInstance.Attenuation.NONE, 0.0D, 0.0D, 0.0D, true));

        if(sound == null)
        {
            Ding.LOGGER.warn("Could not find sound but attempted to play anyway: {}", rl);
        }
    }

    public static SoundSource getCategoryByName(String name)
    {
        for(SoundSource value : SoundSource.values())
        {
            if(value.getName().equalsIgnoreCase(name))
            {
                return value;
            }
        }
        return SoundSource.MASTER;
    }
}
