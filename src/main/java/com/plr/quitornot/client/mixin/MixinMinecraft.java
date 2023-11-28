package com.plr.quitornot.client.mixin;

import com.mojang.blaze3d.platform.Window;
import com.plr.quitornot.client.event.impl.ClientScheduleStopEvent;
import com.plr.quitornot.client.screen.confirm.ConfirmScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow
    @Final
    private Window window;

    @Shadow
    @Nullable
    public Screen screen;

    @Inject(method = "stop", at = @At("HEAD"), cancellable = true)
    private void qon$inject$scheduleStop(CallbackInfo ci) {
        if (screen instanceof ConfirmScreen c) {
            if (c.isConfirmed()) {
                return;
            }
            ci.cancel();
            return;
        }
        final Event event = new ClientScheduleStopEvent();
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            GLFW.glfwSetWindowShouldClose(this.window.getWindow(), false);
            ci.cancel();
        }
    }
}
