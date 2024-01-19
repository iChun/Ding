package me.ichun.mods.ding.common.core;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Config
{
    public ConfigWrapper<Boolean> playOnLoad;
    public ConfigWrapper<String> name;
    public ConfigWrapper<Double> volume;
    public ConfigWrapper<Double> pitch;
    public ConfigWrapper<String> category;

    public ConfigWrapper<Boolean> playOnWorld;
    public ConfigWrapper<String> nameWorld;
    public ConfigWrapper<Double> volumeWorld;
    public ConfigWrapper<Double> pitchWorld;
    public ConfigWrapper<String> categoryWorld;

    public ConfigWrapper<Boolean> playOnResourcesReload;
    public ConfigWrapper<String> nameResourcesReload;
    public ConfigWrapper<Double> volumeResourcesReload;
    public ConfigWrapper<Double> pitchResourcesReload;
    public ConfigWrapper<String> categoryResourcesReload;

    protected static class Reference
    {
        public static final String PLAY_ON_LOAD_COMMENT = "Play sound when the game loads.";
        public static final String NAME_COMMENT = "Resource Location based name of the sound file to play when Minecraft finishes loading.\nEG: \"ui.button.click\" or \"entity.experience_orb.pickup\"\n\nThis can also be a mod sound if the mod is installed.\nEG: \"modname:modsound.boing\"";
        public static final String VOLUME_COMMENT = "Volume of the sound (when Minecraft loads)";
        public static final String PITCH_COMMENT = "Pitch of the sound (when Minecraft loads)";
        public static final String CATEGORY_COMMENT = "Sound category for the sound played when Minecraft finishes loading. EG: \"ambient\" or \"music\". Defaults to \"master\" if Ding cannot find your category.";

        public static final String PLAY_ON_WORLD_COMMENT = "Play sound when the world loads after connecting to a server. Requires game to be restarted when changing this option.";
        public static final String NAME_WORLD_COMMENT = "Resource Location based name of the sound file to play when the world finishes loading (after connecting to a server).\n\nLook at the \"name\" config for more details.";
        public static final String VOLUME_WORLD_COMMENT = "Volume of the sound (when the world loads after connecting to a server)";
        public static final String PITCH_WORLD_COMMENT = "Pitch of the sound (when the world loads after connecting to a server)";
        public static final String CATEGORY_WORLD_COMMENT = "Sound category for the sound played when the world finishes loading (after connecting to a server). EG: \"ambient\" or \"music\". Defaults to \"master\" if Ding cannot find your category.";

        public static final String PLAY_ON_RESOURCES_RELOAD_COMMENT = "Play sound when resources complete reloading. Requires game to be restarted when changing this option.";
        public static final String NAME_RESOURCES_RELOAD_COMMENT = "Resource Location based name of the sound file to play when resources complete reloading.\n\nLook at the \"name\" config for more details.";
        public static final String VOLUME_RESOURCES_RELOAD_COMMENT = "Volume of the sound (when resources complete reloading)";
        public static final String PITCH_RESOURCES_RELOAD_COMMENT = "Pitch of the sound (when resources complete reloading)";
        public static final String CATEGORY_RESOURCES_RELOAD_COMMENT = "Sound category for the sound played when resources complete reloading. EG: \"ambient\" or \"music\". Defaults to \"master\" if Ding cannot find your category.";
    }

    public static class ConfigWrapper<T>
    {
        public final Supplier<T> getter;
        public final Consumer<T> setter;
        public final Runnable saver;

        public ConfigWrapper(Supplier<T> getter, Consumer<T> setter) {
            this.getter = getter;
            this.setter = setter;
            this.saver = null;
        }

        public ConfigWrapper(Supplier<T> getter, Consumer<T> setter, Runnable saver) {
            this.getter = getter;
            this.setter = setter;
            this.saver = saver;
        }

        public T get()
        {
            return getter.get();
        }

        public void set(T obj)
        {
            setter.accept(obj);
        }

        public void save()
        {
            if(saver != null)
            {
                saver.run();
            }
        }
    }
}
