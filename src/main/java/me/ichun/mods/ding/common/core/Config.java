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
