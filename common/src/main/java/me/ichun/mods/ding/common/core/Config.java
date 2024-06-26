package me.ichun.mods.ding.common.core;

import me.ichun.mods.ding.common.Ding;
import me.ichun.mods.ichunutil.common.config.ConfigBase;
import me.ichun.mods.ichunutil.common.config.annotations.CategoryDivider;
import me.ichun.mods.ichunutil.common.config.annotations.Prop;
import org.jetbrains.annotations.NotNull;

public class Config extends ConfigBase
{
    @CategoryDivider(name = "load")
    public boolean playOnLoad = true;
    public String name = "entity.experience_orb.pickup";
    @Prop(min = 0D, max = 10D)
    public double volume = 0.25D;
    @Prop(min = 0D, max = 10D)
    public double pitch = 1D;
    public String category = "master";

    @CategoryDivider(name = "world")
    @Prop(needsRestart = true)
    public boolean playOnWorld = false;
    public String nameWorld = "entity.experience_orb.pickup";
    @Prop(min = 0D, max = 10D)
    public double volumeWorld = 0.25D;
    @Prop(min = 0D, max = 10D)
    public double pitchWorld = 1D;
    public String categoryWorld = "master";

    @CategoryDivider(name = "resourcesReload")
    public boolean playOnResourcesReload = true;
    public String nameResourcesReload = "entity.experience_orb.pickup";
    @Prop(min = 0D, max = 10D)
    public double volumeResourcesReload = 0.25D;
    @Prop(min = 0D, max = 10D)
    public double pitchResourcesReload = 1D;
    public String categoryResourcesReload = "master";

    public Config()
    {
        super(Ding.MOD_ID + ".toml");
    }

    @NotNull
    @Override
    public String getModId()
    {
        return Ding.MOD_ID;
    }

    @NotNull
    @Override
    public String getConfigName()
    {
        return Ding.MOD_NAME;
    }

    @Override
    public Type getConfigType()
    {
        return Type.CLIENT;
    }
}
