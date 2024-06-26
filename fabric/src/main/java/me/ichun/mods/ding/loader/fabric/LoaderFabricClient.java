package me.ichun.mods.ding.loader.fabric;

import me.ichun.mods.ding.common.Ding;
import me.ichun.mods.ding.common.core.Config;
import me.ichun.mods.ichunutil.common.iChunUtil;
import net.fabricmc.api.ClientModInitializer;

public class LoaderFabricClient extends Ding
    implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        modProxy = this;

        //register config
        config = iChunUtil.d().registerConfig(new Config());

        //init event handler
        new EventHandlerClientFabric();
    }
}
