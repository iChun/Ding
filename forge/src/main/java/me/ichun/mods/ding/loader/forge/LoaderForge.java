package me.ichun.mods.ding.loader.forge;

import me.ichun.mods.ding.common.Ding;
import me.ichun.mods.ding.common.core.Config;
import me.ichun.mods.ichunutil.client.gui.config.WorkspaceConfigs;
import me.ichun.mods.ichunutil.common.iChunUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Ding.MOD_ID)
public class LoaderForge extends Ding
{
    public LoaderForge(FMLJavaModLoadingContext context)
    {
        modProxy = this;

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> initClient(context));
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> LOGGER.error("You are loading " + MOD_NAME + " on a server. " + MOD_NAME + " is a client only mod!"));

        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        context.registerExtensionPoint(IExtensionPoint.DisplayTest.class, IExtensionPoint.DisplayTest.IGNORE_ALL_VERSION);
    }

    @OnlyIn(Dist.CLIENT)
    private void initClient(FMLJavaModLoadingContext context)
    {
        config = iChunUtil.d().registerConfig(new Config(), context);

        new EventHandlerClientForge();

        context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> new WorkspaceConfigs(screen, MOD_ID)));
    }
}
