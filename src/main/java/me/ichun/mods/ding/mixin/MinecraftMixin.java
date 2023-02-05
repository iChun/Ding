package me.ichun.mods.ding.mixin;

import me.ichun.mods.ding.common.core.EventHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Overlay;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
    @Inject(method = "setOverlay", at = @At("HEAD"))
    private void setOverlay(@Nullable Overlay overlay, CallbackInfo ci)
    {
        if(!EventHandlerClient.postInit && overlay == null && ((Minecraft)(Object)this).getOverlay() instanceof LoadingOverlay)
        {
            EventHandlerClient.postInit();
        }
    }
}
