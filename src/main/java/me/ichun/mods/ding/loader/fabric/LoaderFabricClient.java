package me.ichun.mods.ding.loader.fabric;

import me.ichun.mods.ding.common.Ding;
import me.lortseam.completeconfig.data.Config;
import net.fabricmc.api.ClientModInitializer;

public class LoaderFabricClient extends Ding
    implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        modProxy = this;

        //register config
        ConfigFabric configFabric = new ConfigFabric();
        config = configFabric;
        Config modConfig = new Config(MOD_ID, new String[]{MOD_ID}, configFabric);
        modConfig.load();;
        Runtime.getRuntime().addShutdownHook(new Thread(modConfig::save));

        //init event handler
        new EventHandlerClientFabric();
    }
}
