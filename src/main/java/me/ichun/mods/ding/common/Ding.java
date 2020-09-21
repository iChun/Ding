package me.ichun.mods.ding.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Ding.MOD_ID)
public class Ding
{
    public static final String MOD_ID = "ding";
    public static final String MOD_NAME = "Ding";

    private static final Logger LOGGER = LogManager.getLogger();

    public static Config config;

    public Ding()
    {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            setupConfig();
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::finishLoading);
        });
        DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> LOGGER.log(Level.ERROR, "You are loading " + MOD_NAME + " on a server. " + MOD_NAME + " is a client only mod!"));

        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }

    private void setupConfig()
    {
        //build the config
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();

        config = new Config(configBuilder);

        //register the config. This loads the config for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, configBuilder.build(), MOD_ID + ".toml");
    }

    private void finishLoading(FMLLoadCompleteEvent event)
    {
        EventHandler.postInit = true;
        if(config.playOnResourcesReload.get())
        {
            MinecraftForge.EVENT_BUS.addListener(this::onClientTick);
        }
    }

    public class Config
    {
        public final ForgeConfigSpec.ConfigValue<String> name;
        public final ForgeConfigSpec.DoubleValue pitch;

        public final ForgeConfigSpec.ConfigValue<String> nameWorld;
        public final ForgeConfigSpec.DoubleValue pitchWorld;

        public final ForgeConfigSpec.ConfigValue<String> nameResourcesReload;
        public final ForgeConfigSpec.DoubleValue pitchResourcesReload;

        public final ForgeConfigSpec.BooleanValue playOnLoad;
        public final ForgeConfigSpec.BooleanValue playOnWorld;
        public final ForgeConfigSpec.BooleanValue playOnResourcesReload;

        public final ForgeConfigSpec.BooleanValue skipSoundEventCheck;

        public Config(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Configs related to how ding works").push("ding");

            name = builder.comment("Resource Location based name of the sound file to play when Minecraft finishes loading.\nEG: \"ui.button.click\" or \"entity.experience_orb.pickup\"\n\nThis can also be a mod sound if the mod is installed.\nEG: \"modname:modsound.boing\"\n\nIf you want to use external sounds, consider looking into the mod Additional Resources")
                    .translation("config.ding.prop.name.desc")
                    .define("name", "entity.experience_orb.pickup");
            pitch = builder.comment("Pitch of the sound (when Minecraft loads)")
                    .translation("config.ding.prop.pitch.desc")
                    .defineInRange("pitch", 1D, 0D, 10D);

            nameWorld = builder.comment("Resource Location based name of the sound file to play when the world finishes loading (after connecting to a server).\n\nLook at the \"name\" config for more details.")
                    .translation("config.ding.prop.nameWorld.desc")
                    .define("nameWorld", "entity.experience_orb.pickup");
            pitchWorld = builder.comment("Pitch of the sound (when the world loads after connecting to a server)")
                    .translation("config.ding.prop.pitchWorld.desc")
                    .defineInRange("pitchWorld", 1D, 0D, 10D);

            nameResourcesReload = builder.comment("Resource Location based name of the sound file to play when resources complete reloading.\n\nLook at the \"name\" config for more details.")
                    .translation("config.ding.prop.nameResourcesReload.desc")
                    .define("nameResourcesReload", "entity.experience_orb.pickup");
            pitchResourcesReload = builder.comment("Pitch of the sound (when resources complete reloading)")
                    .translation("config.ding.prop.pitchResourcesReloadResourcesReload.desc")
                    .defineInRange("pitchResourcesReload", 1D, 0D, 10D);

            playOnLoad = builder.comment("Play sound when the game loads.")
                    .translation("config.ding.prop.playOnLoad.desc")
                    .define("playOnLoad", true);

            playOnWorld = builder.comment("Play sound when the world loads after connecting to a server.")
                    .translation("config.ding.prop.playOnWorld.desc")
                    .define("playOnWorld", false);

            playOnResourcesReload = builder.comment("Play sound when resources complete reloading. Requires game to be restarted.")
                    .translation("config.ding.prop.playOnResourcesReload.desc")
                    .define("playOnResourcesReload", true);

            skipSoundEventCheck = builder.comment("If Ding can't find the third party sound you added with other mods (EG: Additional Resources), try turning this on to skip that check.")
                    .translation("config.ding.prop.skipSoundEventCheck.desc")
                    .define("skipSoundEventCheck", false);

            builder.pop();
        }
    }

    @Mod.EventBusSubscriber(Dist.CLIENT)
    public static class EventHandler
    {
        public static boolean postInit;
        public static boolean played;
        public static boolean playWorld;

        @SubscribeEvent
        public static void onGuiOpen(GuiOpenEvent event)
        {
            if(postInit && event.getGui() instanceof MainMenuScreen && !played)
            {
                played = true;
                if(config.playOnLoad.get())
                {
                    Ding.playSound(config.name.get(), config.pitch.get().floatValue());
                }
            }
        }

        @SubscribeEvent
        public static void onClientLoggedInEvent(ClientPlayerNetworkEvent.LoggedInEvent event)
        {
            playWorld = true;
        }

        @SubscribeEvent
        public static void onWorldTick(TickEvent.WorldTickEvent event)
        {
            if(playWorld && event.phase == TickEvent.Phase.END && Minecraft.getInstance().player != null && (Minecraft.getInstance().player.ticksExisted > 20 || Minecraft.getInstance().isGamePaused()))
            {
                playWorld = false;
                if(config.playOnWorld.get())
                {
                    Ding.playSound(config.nameWorld.get(), config.pitchWorld.get().floatValue());
                }
            }
        }
    }

    private boolean ignoreFirst = true;
    private boolean hasLoadingGui = false;
    private void onClientTick(TickEvent.ClientTickEvent event)
    {
        if(event.phase == TickEvent.Phase.END && config.playOnResourcesReload.get())
        {
            if(Minecraft.getInstance().loadingGui == null && hasLoadingGui)
            {
                if(ignoreFirst) //ignores the loss of the loading GUI when the game is launching
                {
                    ignoreFirst = false;
                }
                else if(config.playOnResourcesReload.get())
                {
                    Ding.playSound(config.nameResourcesReload.get(), config.pitchResourcesReload.get().floatValue());
                }
            }
            hasLoadingGui = Minecraft.getInstance().loadingGui != null;
        }
    }

    public static void playSound(String name, float pitch)
    {
        ResourceLocation rl = new ResourceLocation(name);
        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(rl);
        if(sound != null)
        {
            Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(sound, pitch));
        }
        else if(config.skipSoundEventCheck.get())
        {
            Minecraft.getInstance().getSoundHandler().play(new SimpleSound(rl, SoundCategory.MASTER, 0.25F, pitch, false, 0, ISound.AttenuationType.NONE, 0.0D, 0.0D, 0.0D, true));
        }
        else
        {
            LOGGER.log(Level.WARN, "Could not find sound: {}", rl);
        }
    }
}
