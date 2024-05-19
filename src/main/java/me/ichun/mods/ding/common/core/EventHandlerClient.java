package me.ichun.mods.ding.common.core;

import me.ichun.mods.ding.common.Ding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
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

    public SoundEvent getSoundEvent(ResourceLocation rl)
    {
        return BuiltInRegistries.SOUND_EVENT.get(rl);
    }

    public static void postInit()
    {
        postInit = true;

        if(!played)
        {
            played = true;
            if(Ding.config.playOnLoad)
            {
                playSound(Ding.config.name, (float)Ding.config.volume, (float)Ding.config.pitch, Ding.config.category);
            }
        }

        if(Ding.config.playOnWorld)
        {
            loaderProxy.hookIntoWorldTick();
        }

        if(Ding.config.playOnResourcesReload)
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
            if(Ding.config.playOnWorld)
            {
                playSound(Ding.config.nameWorld, (float)Ding.config.volumeWorld, (float)Ding.config.pitchWorld, Ding.config.categoryWorld);
            }
        }
    }

    public static void onClientTickEnd()
    {
        if(Ding.config.playOnResourcesReload)
        {
            if(Minecraft.getInstance().getOverlay() == null && hasLoadingGui)
            {
                playSound(Ding.config.nameResourcesReload, (float)Ding.config.volumeResourcesReload, (float)Ding.config.pitchResourcesReload, Ding.config.categoryResourcesReload);
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
