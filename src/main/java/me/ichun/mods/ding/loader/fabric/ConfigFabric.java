package me.ichun.mods.ding.loader.fabric;

import me.ichun.mods.ding.common.Ding;
import me.ichun.mods.ding.common.core.Config;
import me.lortseam.completeconfig.api.ConfigContainer;
import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.api.ConfigGroup;

public class ConfigFabric extends Config
        implements ConfigContainer
{
    public static Ding DING = null;

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
    @ConfigEntries
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

        @Transitive
        @ConfigEntries
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

            @ConfigEntry(comment = "Play sound when the game loads.")
            public boolean playOnLoad = true;
            @ConfigEntry(comment = "Resource Location based name of the sound file to play when Minecraft finishes loading.\nEG: \"ui.button.click\" or \"entity.experience_orb.pickup\"\n\nThis can also be a mod sound if the mod is installed.\nEG: \"modname:modsound.boing\"\n\nIf you want to use external sounds, consider looking into the mod Additional Resources")
            public String name = "entity.experience_orb.pickup";
            @ConfigEntry(comment = "Volume of the sound (when Minecraft loads)")
            @ConfigEntry.BoundedDouble(min = 0D, max = 10D)
            public double volume = 0.25D;
            @ConfigEntry(comment = "Pitch of the sound (when Minecraft loads)")
            @ConfigEntry.BoundedDouble(min = 0D, max = 10D)
            public double pitch = 1D;
            @ConfigEntry(comment = "Sound category for the sound played when Minecraft finishes loading. EG: \"ambient\" or \"music\". Defaults to \"master\" if Ding cannot find your category.")
            public String category = "master";
        }

        @Transitive
        @ConfigEntries
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

            @ConfigEntry(comment = "Play sound when the world loads after connecting to a server. Requires game to be restarted when changing this option.", requiresRestart = true)
            public boolean playOnWorld = false;
            @ConfigEntry(comment = "Resource Location based name of the sound file to play when the world finishes loading (after connecting to a server).\n\nLook at the \"name\" config for more details.")
            public String nameWorld = "entity.experience_orb.pickup";
            @ConfigEntry(comment = "Volume of the sound (when the world loads after connecting to a server)")
            @ConfigEntry.BoundedDouble(min = 0D, max = 10D)
            public double volumeWorld = 0.25D;
            @ConfigEntry(comment = "Pitch of the sound (when the world loads after connecting to a server)")
            @ConfigEntry.BoundedDouble(min = 0D, max = 10D)
            public double pitchWorld = 1D;
            @ConfigEntry(comment = "Sound category for the sound played when the world finishes loading (after connecting to a server). EG: \"ambient\" or \"music\". Defaults to \"master\" if Ding cannot find your category.")
            public String categoryWorld = "master";
        }

        @Transitive
        @ConfigEntries
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

            @ConfigEntry(comment = "Play sound when resources complete reloading. Requires game to be restarted when changing this option.", requiresRestart = true)
            public boolean playOnResourcesReload = true;
            @ConfigEntry(comment = "Resource Location based name of the sound file to play when resources complete reloading.\n\nLook at the \"name\" config for more details.")
            public String nameResourcesReload = "entity.experience_orb.pickup";
            @ConfigEntry(comment = "Volume of the sound (when resources complete reloading)")
            @ConfigEntry.BoundedDouble(min = 0D, max = 10D)
            public double volumeResourcesReload = 0.25D;
            @ConfigEntry(comment = "Pitch of the sound (when resources complete reloading)")
            @ConfigEntry.BoundedDouble(min = 0D, max = 10D)
            public double pitchResourcesReload = 1D;
            @ConfigEntry(comment = "Sound category for the sound played when resources complete reloading. EG: \"ambient\" or \"music\". Defaults to \"master\" if Ding cannot find your category.")
            public String categoryResourcesReload = "master";
        }
    }
}
