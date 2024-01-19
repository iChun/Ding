package me.ichun.mods.ding.loader.fabric;

import me.ichun.mods.ding.common.core.Config;
import me.lortseam.completeconfig.api.ConfigContainer;
import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.api.ConfigGroup;

public class ConfigFabric extends Config
    implements ConfigContainer
{
    public static Ding DING = null;

    public me.lortseam.completeconfig.data.Config configInstance;

    public ConfigFabric()
    {
        playOnLoad = new ConfigWrapper<>(() -> DING.LOAD.playOnLoad, v -> DING.LOAD.playOnLoad = v);
        name = new ConfigWrapper<>(() -> DING.LOAD.name, v -> DING.LOAD.name = v);
        volume = new ConfigWrapper<>(() -> DING.LOAD.volume, v -> DING.LOAD.volume = v);
        pitch = new ConfigWrapper<>(() -> DING.LOAD.pitch, v -> DING.LOAD.pitch = v);
        category = new ConfigWrapper<>(() -> DING.LOAD.category, v -> DING.LOAD.category = v);

        playOnWorld = new ConfigWrapper<>(() -> DING.WORLD.playOnWorld, v -> DING.WORLD.playOnWorld = v);
        nameWorld = new ConfigWrapper<>(() -> DING.WORLD.nameWorld, v -> DING.WORLD.nameWorld = v);
        volumeWorld = new ConfigWrapper<>(() -> DING.WORLD.volumeWorld, v -> DING.WORLD.volumeWorld = v);
        pitchWorld = new ConfigWrapper<>(() -> DING.WORLD.pitchWorld, v -> DING.WORLD.pitchWorld = v);
        categoryWorld = new ConfigWrapper<>(() -> DING.WORLD.categoryWorld, v -> DING.WORLD.categoryWorld = v);

        playOnResourcesReload = new ConfigWrapper<>(() -> DING.RESOURCES_RELOAD.playOnResourcesReload, v -> DING.RESOURCES_RELOAD.playOnResourcesReload = v);
        nameResourcesReload = new ConfigWrapper<>(() -> DING.RESOURCES_RELOAD.nameResourcesReload, v -> DING.RESOURCES_RELOAD.nameResourcesReload = v);
        volumeResourcesReload = new ConfigWrapper<>(() -> DING.RESOURCES_RELOAD.volumeResourcesReload, v -> DING.RESOURCES_RELOAD.volumeResourcesReload = v);
        pitchResourcesReload = new ConfigWrapper<>(() -> DING.RESOURCES_RELOAD.pitchResourcesReload, v -> DING.RESOURCES_RELOAD.pitchResourcesReload = v);
        categoryResourcesReload = new ConfigWrapper<>(() -> DING.RESOURCES_RELOAD.categoryResourcesReload, v -> DING.RESOURCES_RELOAD.categoryResourcesReload = v);
    }

    @Transitive
    @ConfigEntries(includeAll = true)
    public static class Ding implements ConfigGroup
    {
        public static Load LOAD;
        public static World WORLD;
        public static ResourcesReload RESOURCES_RELOAD;

        public Ding()
        {
            DING = this;
        }

        @Override
        public String getComment()
        {
            return "Configs related to how ding works";
        }

        @Override
        public String getNameKey()
        {
            return "cat.ding.name";
        }

        @Override
        public String getDescriptionKey()
        {
            return "cat.ding.desc";
        }

        @Transitive
        @ConfigEntries(includeAll = true)
        public static class Load implements ConfigGroup
        {
            public Load()
            {
                LOAD = this;
            }

            @Override
            public String getComment()
            {
                return "Configs for ding to trigger when the game loads";
            }

            @Override
            public String getNameKey()
            {
                return "cat.ding.load.name";
            }

            @Override
            public String getDescriptionKey()
            {
                return "cat.ding.load.desc";
            }

            @ConfigEntry(nameKey = "prop.playOnLoad.name", descriptionKey = "prop.playOnLoad.desc", comment = Reference.PLAY_ON_LOAD_COMMENT)
            public boolean playOnLoad = true;
            @ConfigEntry(nameKey = "prop.name.name", descriptionKey = "prop.name.desc", comment = Reference.NAME_COMMENT)
            public String name = "entity.experience_orb.pickup";
            @ConfigEntry(nameKey = "prop.volume.name", descriptionKey = "prop.volume.desc", comment = Reference.VOLUME_COMMENT)
            @ConfigEntry.BoundedDouble(min = 0D, max = 10D)
            public double volume = 0.25D;
            @ConfigEntry(nameKey = "prop.pitch.name", descriptionKey = "prop.pitch.desc", comment = Reference.PITCH_COMMENT)
            @ConfigEntry.BoundedDouble(min = 0D, max = 10D)
            public double pitch = 1D;
            @ConfigEntry(nameKey = "prop.category.name", descriptionKey = "prop.category.desc", comment = Reference.CATEGORY_COMMENT)
            public String category = "master";
        }

        @Transitive
        @ConfigEntries(includeAll = true)
        public static class World implements ConfigGroup
        {
            public World()
            {
                WORLD = this;
            }

            @Override
            public String getComment()
            {
                return "Configs for ding to trigger when the world loads after connecting to a server";
            }

            @Override
            public String getNameKey()
            {
                return "cat.ding.world.name";
            }

            @Override
            public String getDescriptionKey()
            {
                return "cat.ding.world.desc";
            }

            @ConfigEntry(nameKey = "prop.playOnWorld.name", descriptionKey = "prop.playOnWorld.desc", comment = Reference.PLAY_ON_WORLD_COMMENT, requiresRestart = true)
            public boolean playOnWorld = false;
            @ConfigEntry(nameKey = "prop.nameWorld.name", descriptionKey = "prop.nameWorld.desc", comment = Reference.NAME_WORLD_COMMENT)
            public String nameWorld = "entity.experience_orb.pickup";
            @ConfigEntry(nameKey = "prop.volumeWorld.name", descriptionKey = "prop.volumeWorld.desc", comment = Reference.VOLUME_WORLD_COMMENT)
            @ConfigEntry.BoundedDouble(min = 0D, max = 10D)
            public double volumeWorld = 0.25D;
            @ConfigEntry(nameKey = "prop.pitchWorld.name", descriptionKey = "prop.pitchWorld.desc", comment = Reference.PITCH_WORLD_COMMENT)
            @ConfigEntry.BoundedDouble(min = 0D, max = 10D)
            public double pitchWorld = 1D;
            @ConfigEntry(nameKey = "prop.categoryWorld.name", descriptionKey = "prop.categoryWorld.desc", comment = Reference.CATEGORY_WORLD_COMMENT)
            public String categoryWorld = "master";
        }

        @Transitive
        @ConfigEntries(includeAll = true)
        public static class ResourcesReload implements ConfigGroup
        {
            public ResourcesReload()
            {
                RESOURCES_RELOAD = this;
            }

            @Override
            public String getComment()
            {
                return "Configs for ding to trigger when resources are reloaded";
            }

            @Override
            public String getNameKey()
            {
                return "cat.ding.resourcesReload.name";
            }

            @Override
            public String getDescriptionKey()
            {
                return "cat.ding.resourcesReload.desc";
            }

            @ConfigEntry(nameKey = "prop.playOnResourcesReload.name", descriptionKey = "prop.playOnResourcesReload.desc", comment = Reference.PLAY_ON_RESOURCES_RELOAD_COMMENT, requiresRestart = true)
            public boolean playOnResourcesReload = true;
            @ConfigEntry(nameKey = "prop.nameResourcesReload.name", descriptionKey = "prop.nameResourcesReload.desc", comment = Reference.NAME_RESOURCES_RELOAD_COMMENT)
            public String nameResourcesReload = "entity.experience_orb.pickup";
            @ConfigEntry(nameKey = "prop.volumeResourcesReload.name", descriptionKey = "prop.volumeResourcesReload.desc", comment = Reference.VOLUME_RESOURCES_RELOAD_COMMENT)
            @ConfigEntry.BoundedDouble(min = 0D, max = 10D)
            public double volumeResourcesReload = 0.25D;
            @ConfigEntry(nameKey = "prop.pitchResourcesReload.name", descriptionKey = "prop.pitchResourcesReload.desc", comment = Reference.PITCH_RESOURCES_RELOAD_COMMENT)
            @ConfigEntry.BoundedDouble(min = 0D, max = 10D)
            public double pitchResourcesReload = 1D;
            @ConfigEntry(nameKey = "prop.categoryResourcesReload.name", descriptionKey = "prop.categoryResourcesReload.desc", comment = Reference.CATEGORY_RESOURCES_RELOAD_COMMENT)
            public String categoryResourcesReload = "master";
        }
    }
}
