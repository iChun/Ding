package me.ichun.mods.ding.loader.neoforge;

import me.ichun.mods.ding.common.Ding;
import me.ichun.mods.ding.common.core.Config;
import me.ichun.mods.ichunutil.client.gui.config.WorkspaceConfigs;
import me.ichun.mods.ichunutil.common.iChunUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.function.Supplier;

@Mod(value = Ding.MOD_ID, dist = Dist.CLIENT)
public class LoaderNeoForge extends Ding
{
    public LoaderNeoForge(IEventBus modEventBus, ModContainer container)
    {
        modProxy = this;

        if(FMLEnvironment.dist.isClient())
        {
            initClient(modEventBus, container);
        }
        else
        {
            LOGGER.error("You are loading " + MOD_NAME + " on a server. " + MOD_NAME + " is a client only mod!");
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void initClient(IEventBus modEventBus, ModContainer container)
    {
        Ding.config = iChunUtil.d().registerConfig(new Config(), modEventBus);

        new EventHandlerClientNeoForge();

        container.registerExtensionPoint(IConfigScreenFactory.class, (Supplier<IConfigScreenFactory>)() -> (modContainer, screen) -> new WorkspaceConfigs(screen, MOD_ID));
    }
}
