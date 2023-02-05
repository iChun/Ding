package me.ichun.mods.ding.common;

import com.mojang.logging.LogUtils;
import me.ichun.mods.ding.common.core.Config;
import org.slf4j.Logger;

public abstract class Ding
{
    public static final String MOD_ID = "ding";
    public static final String MOD_NAME = "Ding";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static Ding modProxy;

    public static Config config;
}
