package com.plr.quitornot.client.mixin;

import com.plr.quitornot.client.event.ClientScheduleStopEvent;
import com.plr.quitornot.client.event.EventResult;
import com.plr.quitornot.client.screen.confirm.ConfirmScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Shadow
    @Nullable
    public Screen currentScreen;
    @Shadow
    @Final
    private Window window;

    @Inject(method = "scheduleStop", at = @At("HEAD"), cancellable = true)
    private void qon$inject$scheduleStop(CallbackInfo ci) {
        if (currentScreen instanceof ConfirmScreen) {
            if (((ConfirmScreen) currentScreen).isConfirmed()) {
                return;
            }
            ci.cancel();
            return;
        }
        EventResult result = ClientScheduleStopEvent.CLIENT_SCHEDULE_STOP.invoker().onScheduleStop();
        if (result == EventResult.CANCEL) {
            GLFW.glfwSetWindowShouldClose(this.window.getHandle(), false);
            ci.cancel();
        }
    }
}
