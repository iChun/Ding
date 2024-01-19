package me.ichun.mods.ding.loader.neoforge;

import me.ichun.mods.ding.common.core.Config;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigNeoforge extends Config
{
    public ConfigNeoforge(ModConfigSpec.Builder builder)
    {
        builder.comment("Configs related to how ding works").push("ding");

        builder.comment("Configs for ding to trigger when the game loads").push("load");
        //Load configs
        final ModConfigSpec.BooleanValue cPlayOnLoad = builder.comment(Reference.PLAY_ON_LOAD_COMMENT).translation("config.ding.prop.playOnLoad.desc").define("playOnLoad", true);
        playOnLoad = new ConfigWrapper<>(cPlayOnLoad::get, cPlayOnLoad::set, cPlayOnLoad::save);

        final ModConfigSpec.ConfigValue<String> cName = builder.comment(Reference.NAME_COMMENT).translation("config.ding.prop.name.desc").define("name", "entity.experience_orb.pickup");
        name = new ConfigWrapper<>(cName::get, cName::set, cName::save);

        final ModConfigSpec.DoubleValue cVolume = builder.comment(Reference.VOLUME_COMMENT).translation("config.ding.prop.volume.desc").defineInRange("volume", 0.25D, 0D, 10D);
        volume = new ConfigWrapper<>(cVolume::get, cVolume::set, cVolume::save);

        final ModConfigSpec.DoubleValue cPitch = builder.comment(Reference.PITCH_COMMENT).translation("config.ding.prop.pitch.desc").defineInRange("pitch", 1D, 0D, 10D);
        pitch = new ConfigWrapper<>(cPitch::get, cPitch::set, cPitch::save);

        final ModConfigSpec.ConfigValue<String> cCategory = builder.comment(Reference.CATEGORY_COMMENT).translation("config.ding.prop.category.desc").define("category", "master");
        category = new ConfigWrapper<>(cCategory::get, cCategory::set, cCategory::save);

        builder.pop();

        builder.comment("Configs for ding to trigger when the world loads after connecting to a server").push("world");

        //World configs
        final ModConfigSpec.BooleanValue cPlayOnWorld = builder.comment(Reference.PLAY_ON_WORLD_COMMENT).translation("config.ding.prop.playOnWorld.desc").worldRestart().define("playOnWorld", false);
        playOnWorld = new ConfigWrapper<>(cPlayOnWorld::get, cPlayOnWorld::set, cPlayOnWorld::save);

        final ModConfigSpec.ConfigValue<String> cNameWorld = builder.comment(Reference.NAME_WORLD_COMMENT).translation("config.ding.prop.nameWorld.desc").define("nameWorld", "entity.experience_orb.pickup");
        nameWorld = new ConfigWrapper<>(cNameWorld::get, cNameWorld::set, cNameWorld::save);

        final ModConfigSpec.DoubleValue cVolumeWorld = builder.comment(Reference.VOLUME_WORLD_COMMENT).translation("config.ding.prop.volumeWorld.desc").defineInRange("volumeWorld", 0.25D, 0D, 10D);
        volumeWorld = new ConfigWrapper<>(cVolumeWorld::get, cVolumeWorld::set, cVolumeWorld::save);

        final ModConfigSpec.DoubleValue cPitchWorld = builder.comment(Reference.PITCH_WORLD_COMMENT).translation("config.ding.prop.pitchWorld.desc").defineInRange("pitchWorld", 1D, 0D, 10D);
        pitchWorld = new ConfigWrapper<>(cPitchWorld::get, cPitchWorld::set, cPitchWorld::save);

        final ModConfigSpec.ConfigValue<String> cCategoryWorld = builder.comment(Reference.CATEGORY_WORLD_COMMENT).translation("config.ding.prop.categoryWorld.desc").define("categoryWorld", "master");
        categoryWorld = new ConfigWrapper<>(cCategoryWorld::get, cCategoryWorld::set, cCategoryWorld::save);

        builder.pop();

        builder.comment("Configs for ding to trigger when resources are reloaded").push("resourcesReload");

        //Resource reload configs
        final ModConfigSpec.BooleanValue cPlayOnResourcesReload = builder.comment(Reference.PLAY_ON_RESOURCES_RELOAD_COMMENT).translation("config.ding.prop.playOnResourcesReload.desc").worldRestart().define("playOnResourcesReload", true);
        playOnResourcesReload = new ConfigWrapper<>(cPlayOnResourcesReload::get, cPlayOnResourcesReload::set, cPlayOnResourcesReload::save);

        final ModConfigSpec.ConfigValue<String> cNameResourcesReload = builder.comment(Reference.NAME_RESOURCES_RELOAD_COMMENT).translation("config.ding.prop.nameResourcesReload.desc").define("nameResourcesReload", "entity.experience_orb.pickup");
        nameResourcesReload = new ConfigWrapper<>(cNameResourcesReload::get, cNameResourcesReload::set, cNameResourcesReload::save);

        final ModConfigSpec.DoubleValue cVolumeResourcesReload = builder.comment(Reference.VOLUME_RESOURCES_RELOAD_COMMENT).translation("config.ding.prop.volumeResourcesReloadResourcesReload.desc").defineInRange("volumeResourcesReload", 0.25D, 0D, 10D);
        volumeResourcesReload = new ConfigWrapper<>(cVolumeResourcesReload::get, cVolumeResourcesReload::set, cVolumeResourcesReload::save);

        final ModConfigSpec.DoubleValue cPitchResourcesReload = builder.comment(Reference.PITCH_RESOURCES_RELOAD_COMMENT).translation("config.ding.prop.pitchResourcesReloadResourcesReload.desc").defineInRange("pitchResourcesReload", 1D, 0D, 10D);
        pitchResourcesReload = new ConfigWrapper<>(cPitchResourcesReload::get, cPitchResourcesReload::set, cPitchResourcesReload::save);

        final ModConfigSpec.ConfigValue<String> cCategoryResourcesReload = builder.comment(Reference.CATEGORY_RESOURCES_RELOAD_COMMENT).translation("config.ding.prop.categoryResourcesReload.desc").define("categoryResourcesReload", "master");
        categoryResourcesReload = new ConfigWrapper<>(cCategoryResourcesReload::get, cCategoryResourcesReload::set, cCategoryResourcesReload::save);

        builder.pop();

        builder.pop();
    }

}
