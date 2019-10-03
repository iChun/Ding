package me.ichun.mods.ding.common.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

@Mod(modid = "ding", name = "Ding",
        version = Ding.VERSION,
        certificateFingerprint = Ding.CERT_FINGERPRINT,
        clientSideOnly = true,
        acceptableRemoteVersions = "*",
        dependencies = "required-after:forge@[13.19.0.2141,)",
        acceptedMinecraftVersions = "[1.12,1.13)"
)
public class Ding
{
    public static final String VERSION = "1.0.2";

    public static final String CERT_FINGERPRINT = "4db5c2bd1b556f252a5b8b54b256d381b2a0a6b8";

    public static String name = "entity.experience_orb.pickup";
    public static double pitch = 1.0D;

    public static String nameWorld = "entity.experience_orb.pickup";
    public static double pitchWorld = 1.0D;

    public static int playOn = 1;

    @Instance("ding")
    public static Ding instance;
    private static Logger logger;

    public static boolean postInit = false;
    public static boolean played = false;
    public static boolean playWorld = false;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        name = config.getString("name", "ding", name, "Minecraft name of sound file to play when Minecraft is loaded\nEG: \"ui.button.click\" or \"entity.experience_orb.pickup\"\n\n" + "This can also be a mod sound if the mod is installed.\nEG: modname:modsound.boing\n\nIf you want to use external sounds, consider looking into the mod Additional Resources\n", "entity.experience_orb.pickup");
        pitch = (double)config.getFloat("pitch", "ding", (float)pitch, 0F, 10F, "Pitch of the sound to play when Minecraft is loaded");

        nameWorld = config.getString("nameWorld", "ding", nameWorld, "Minecraft name of sound file to play when world is loaded\nEG: \"ui.button.click\" or \"entity.experience_orb.pickup\"\n\n" + "This can also be a mod sound if the mod is installed.\nEG: modname:modsound.boing\n\nIf you want to use external sounds, consider looking into the mod Additional Resources\n", "entity.experience_orb.pickup");
        pitchWorld = (double)config.getFloat("pitchWorld", "ding", (float)pitchWorld, 0F, 10F, "Pitch of the sound to play when world is loaded");

        playOn = config.getInt("playOn", "ding", playOn, 0, 3, "Play sound on...\n0 = Nothing (why install the mod though?)\n1 = MC load\n2 = World load\n3 = MC and World load");

        if(config.hasChanged())
        {
            config.save();
        }

        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        postInit = true;
    }

    @EventHandler
    public void onFingerprintViolation(FMLFingerprintViolationEvent event)
    {
        if(event.getSource() != null && event.getSource().isFile())
        {
            logger.warn("The file " + event.getSource().getName() + " has been modified. Support for the mod will not be provided.");
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void onGuiOpen(GuiOpenEvent event)
    {
        if(postInit && event.getGui() instanceof GuiMainMenu && !played)
        {
            played = true;
            if(playOn == 1 || playOn == 3)
            {
                SoundEvent sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(name));
                if(sound != null)
                {
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(sound, (float)pitch));
                }
                else
                {
                    logger.log(Level.WARN, "Could not find sound: {}", new ResourceLocation(name));
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onConnectToServer(FMLNetworkEvent.ClientConnectedToServerEvent event)
    {
        playWorld = true;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if(playWorld && event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().player != null && (Minecraft.getMinecraft().player.ticksExisted > 20 || Minecraft.getMinecraft().isGamePaused()))
        {
            playWorld = false;
            if(playOn == 2 || playOn == 3)
            {
                SoundEvent sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(nameWorld));
                if(sound != null)
                {
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(sound, (float)pitchWorld));
                }
                else
                {
                    logger.log(Level.WARN, "Could not find sound: {}", new ResourceLocation(nameWorld));
                }
            }
        }
    }
}
