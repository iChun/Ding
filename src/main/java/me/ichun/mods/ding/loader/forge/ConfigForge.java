package me.ichun.mods.ding.loader.forge;

import me.ichun.mods.ding.common.core.Config;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigForge extends Config
{
    public ConfigForge(ForgeConfigSpec.Builder builder)
    {
        builder.comment("Configs related to how ding works").push("ding");

        builder.comment("Configs for ding to trigger when the game loads").push("load");
        //Load configs
        final ForgeConfigSpec.BooleanValue cPlayOnLoad = builder.comment("Play sound when the game loads.").translation("config.ding.prop.playOnLoad.desc").define("playOnLoad", true);
        playOnLoad = new ConfigWrapper<>(cPlayOnLoad::get, cPlayOnLoad::set, cPlayOnLoad::save);

        final ForgeConfigSpec.ConfigValue<String> cName = builder.comment("Resource Location based name of the sound file to play when Minecraft finishes loading.\nEG: \"ui.button.click\" or \"entity.experience_orb.pickup\"\n\nThis can also be a mod sound if the mod is installed.\nEG: \"modname:modsound.boing\"\n\nIf you want to use external sounds, consider looking into the mod Additional Resources").translation("config.ding.prop.name.desc").define("name", "entity.experience_orb.pickup");
        name = new ConfigWrapper<>(cName::get, cName::set, cName::save);

        final ForgeConfigSpec.DoubleValue cVolume = builder.comment("Volume of the sound (when Minecraft loads)").translation("config.ding.prop.volume.desc").defineInRange("volume", 0.25D, 0D, 10D);
        volume = new ConfigWrapper<>(cVolume::get, cVolume::set, cVolume::save);

        final ForgeConfigSpec.DoubleValue cPitch = builder.comment("Pitch of the sound (when Minecraft loads)").translation("config.ding.prop.pitch.desc").defineInRange("pitch", 1D, 0D, 10D);
        pitch = new ConfigWrapper<>(cPitch::get, cPitch::set, cPitch::save);

        final ForgeConfigSpec.ConfigValue<String> cCategory = builder.comment("Sound category for the sound played when Minecraft finishes loading. EG: \"ambient\" or \"music\". Defaults to \"master\" if Ding cannot find your category.").translation("config.ding.prop.category.desc").define("category", "master");
        category = new ConfigWrapper<>(cCategory::get, cCategory::set, cCategory::save);

        builder.pop();

        builder.comment("Configs for ding to trigger when the world loads after connecting to a server").push("world");

        //World configs
        final ForgeConfigSpec.BooleanValue cPlayOnWorld = builder.comment("Play sound when the world loads after connecting to a server.").translation("config.ding.prop.playOnWorld.desc").worldRestart().define("playOnWorld", false);
        playOnWorld = new ConfigWrapper<>(cPlayOnWorld::get, cPlayOnWorld::set, cPlayOnWorld::save);

        final ForgeConfigSpec.ConfigValue<String> cNameWorld = builder.comment("Resource Location based name of the sound file to play when the world finishes loading (after connecting to a server).\n\nLook at the \"name\" config for more details.").translation("config.ding.prop.nameWorld.desc").define("nameWorld", "entity.experience_orb.pickup");
        nameWorld = new ConfigWrapper<>(cNameWorld::get, cNameWorld::set, cNameWorld::save);

        final ForgeConfigSpec.DoubleValue cVolumeWorld = builder.comment("Volume of the sound (when the world loads after connecting to a server)").translation("config.ding.prop.volumeWorld.desc").defineInRange("volumeWorld", 0.25D, 0D, 10D);
        volumeWorld = new ConfigWrapper<>(cVolumeWorld::get, cVolumeWorld::set, cVolumeWorld::save);

        final ForgeConfigSpec.DoubleValue cPitchWorld = builder.comment("Pitch of the sound (when the world loads after connecting to a server)").translation("config.ding.prop.pitchWorld.desc").defineInRange("pitchWorld", 1D, 0D, 10D);
        pitchWorld = new ConfigWrapper<>(cPitchWorld::get, cPitchWorld::set, cPitchWorld::save);

        final ForgeConfigSpec.ConfigValue<String> cCategoryWorld = builder.comment("Sound category for the sound played when the world finishes loading (after connecting to a server). EG: \"ambient\" or \"music\". Defaults to \"master\" if Ding cannot find your category.").translation("config.ding.prop.categoryWorld.desc").define("categoryWorld", "master");
        categoryWorld = new ConfigWrapper<>(cCategoryWorld::get, cCategoryWorld::set, cCategoryWorld::save);

        builder.pop();

        builder.comment("Configs for ding to trigger when resources are reloaded").push("resourcesReload");

        //Resource reload configs
        final ForgeConfigSpec.BooleanValue cPlayOnResourcesReload = builder.comment("Play sound when resources complete reloading. Requires game to be restarted.").translation("config.ding.prop.playOnResourcesReload.desc").worldRestart().define("playOnResourcesReload", true);
        playOnResourcesReload = new ConfigWrapper<>(cPlayOnResourcesReload::get, cPlayOnResourcesReload::set, cPlayOnResourcesReload::save);

        final ForgeConfigSpec.ConfigValue<String> cNameResourcesReload = builder.comment("Resource Location based name of the sound file to play when resources complete reloading.\n\nLook at the \"name\" config for more details.").translation("config.ding.prop.nameResourcesReload.desc").define("nameResourcesReload", "entity.experience_orb.pickup");
        nameResourcesReload = new ConfigWrapper<>(cNameResourcesReload::get, cNameResourcesReload::set, cNameResourcesReload::save);

        final ForgeConfigSpec.DoubleValue cVolumeResourcesReload = builder.comment("Volume of the sound (when resources complete reloading)").translation("config.ding.prop.volumeResourcesReloadResourcesReload.desc").defineInRange("volumeResourcesReload", 0.25D, 0D, 10D);
        volumeResourcesReload = new ConfigWrapper<>(cVolumeResourcesReload::get, cVolumeResourcesReload::set, cVolumeResourcesReload::save);

        final ForgeConfigSpec.DoubleValue cPitchResourcesReload = builder.comment("Pitch of the sound (when resources complete reloading)").translation("config.ding.prop.pitchResourcesReloadResourcesReload.desc").defineInRange("pitchResourcesReload", 1D, 0D, 10D);
        pitchResourcesReload = new ConfigWrapper<>(cPitchResourcesReload::get, cPitchResourcesReload::set, cPitchResourcesReload::save);

        final ForgeConfigSpec.ConfigValue<String> cCategoryResourcesReload = builder.comment("Sound category for the sound played when resources complete reloading. EG: \"ambient\" or \"music\". Defaults to \"master\" if Ding cannot find your category.").translation("config.ding.prop.categoryResourcesReload.desc").define("categoryResourcesReload", "master");
        categoryResourcesReload = new ConfigWrapper<>(cCategoryResourcesReload::get, cCategoryResourcesReload::set, cCategoryResourcesReload::save);

        builder.pop();

        builder.pop();
    }

}
