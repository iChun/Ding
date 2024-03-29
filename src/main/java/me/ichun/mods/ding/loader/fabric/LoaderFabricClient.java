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
        configFabric.configInstance = new Config(MOD_ID, new String[]{}, configFabric);
        configFabric.configInstance.load();
        Runtime.getRuntime().addShutdownHook(new Thread(configFabric.configInstance::save));

        //init event handler
        new EventHandlerClientFabric();
    }
}
