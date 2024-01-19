package me.ichun.mods.ding.loader.neoforge;

import me.ichun.mods.ding.common.Ding;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.ModConfigSpec;

@Mod(Ding.MOD_ID)
public class LoaderNeoforge extends Ding
{
    public LoaderNeoforge(IEventBus modEventBus)
    {
        modProxy = this;

        if(FMLEnvironment.dist.isClient())
        {
            initClient();
        }
        else
        {
            LOGGER.error("You are loading " + MOD_NAME + " on a server. " + MOD_NAME + " is a client only mod!");
        }

        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
    }

    @OnlyIn(Dist.CLIENT)
    private void initClient()
    {
        setupConfig();
        new EventHandlerClientNeoforge();
    }

    @OnlyIn(Dist.CLIENT)
    private void setupConfig()
    {
        //build the config
        ModConfigSpec.Builder configBuilder = new ModConfigSpec.Builder();
        config = new ConfigNeoforge(configBuilder);
        //register the config. This loads the config for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, configBuilder.build(), MOD_ID + ".toml");
    }
}
