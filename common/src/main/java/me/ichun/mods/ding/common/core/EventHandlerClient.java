package me.ichun.mods.ding.common.core;

import me.ichun.mods.ding.common.Ding;
import me.ichun.mods.ichunutil.common.iChunUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;

public abstract class EventHandlerClient
{
    //This is here instead of normally being in the mod class because it is only classloaded when called by the client.
    public static EventHandlerClient loaderProxy;

    private static boolean hasInit;
    private static boolean playWorld;

    public static boolean init()
    {
        if(!hasInit)
        {
            hasInit = true;
            if(Ding.config.playOnLoad)
            {
                playSound(Ding.config.name, (float)Ding.config.volume, (float)Ding.config.pitch, Ding.config.category);
            }

            if(Ding.config.playOnWorld)
            {
                iChunUtil.eC().registerOnClientConnectListener(client -> promptToPlayWorld());
                iChunUtil.eC().registerClientTickEndListener(client -> onClientTickEnd());
            }

            return true;
        }

        return false;
    }

    public static void onOverlayChange(@Nullable Overlay currentOverlay, @Nullable Overlay newOverlay)
    {
        if(currentOverlay instanceof LoadingOverlay && newOverlay == null)
        {
            if(!init() && Ding.config.playOnResourcesReload) //we have loaded and played the sound before - check if we're reloading resources
            {
                playSound(Ding.config.nameResourcesReload, (float)Ding.config.volumeResourcesReload, (float)Ding.config.pitchResourcesReload, Ding.config.categoryResourcesReload);
            }
        }
    }

    public static void promptToPlayWorld()
    {
        if(Ding.config.playOnWorld)
        {
            playWorld = true;
        }
    }

    public static void onClientTickEnd()
    {
        Minecraft mc = Minecraft.getInstance();
        if(playWorld && mc.player != null && (mc.player.tickCount > 20 || mc.isPaused()))
        {
            playWorld = false;
            playSound(Ding.config.nameWorld, (float)Ding.config.volumeWorld, (float)Ding.config.pitchWorld, Ding.config.categoryWorld);
        }
    }

    public static void playSound(String name, float volume, float pitch, String categoryName)
    {
        ResourceLocation rl = new ResourceLocation(name);
        SoundEvent sound = iChunUtil.d().registrySoundEvents(rl);
        SoundSource category = getCategoryByName(categoryName);

        //if the sound doesn't exist we play a missing sound
        Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(sound == null ? rl : sound.getLocation(), category, volume, pitch, false, 0, SoundInstance.Attenuation.NONE, 0.0D, 0.0D, 0.0D, true));

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
