package com.plr.quitornot.client.event.handler;

import com.plr.quitornot.client.config.ClientConfig;
import com.plr.quitornot.client.event.impl.ButtonPressEvent;
import com.plr.quitornot.client.event.impl.ClientScheduleStopEvent;
import com.plr.quitornot.client.handler.ToastQuitHandler;
import com.plr.quitornot.client.screen.confirm.ConfirmScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ForgeEventHandler {
    private static final ToastQuitHandler toastInFinalQuitHandler = new ToastQuitHandler(Component.translatable("toast.quitornot.confirm.title.game"));
    private static final ToastQuitHandler toastInSinglePlayerQuitHandle = new ToastQuitHandler(Component.translatable("toast.quitornot.confirm.title.singleplayer"));
    private static final ToastQuitHandler toastInMultiplayerQuitHandle = new ToastQuitHandler(Component.translatable("toast.quitornot.confirm.title.multiplayer"));

    @SubscribeEvent
    public static void onButtonPress(ButtonPressEvent event) {
        final Button button = event.getButton();
        if (!(Minecraft.getInstance().screen instanceof PauseScreen)) {
            return;
        }
        String key = null;
        if (button.getMessage() instanceof TranslatableContents) {
            key = ((TranslatableContents) button.getMessage()).getKey();
        } else if (button.getMessage().getContents() instanceof TranslatableContents) {
            key = ((TranslatableContents) button.getMessage().getContents()).getKey();
        }
        if ("menu.returnToMenu".equals(key)) {
            if (ClientConfig.confirmTypeQuitSinglePlayer.get() == ClientConfig.ConfirmType.TOAST) {
                if (toastInSinglePlayerQuitHandle.trigger()) event.setCanceled(true);
                return;
            }
            if (ClientConfig.confirmTypeQuitSinglePlayer.get() == ClientConfig.ConfirmType.SCREEN) {
                Minecraft.getInstance().setScreen(new ConfirmScreen(Minecraft.getInstance().screen, Component.translatable("screen.quitornot.confirm.title.singleplayer"), button::onPress));
                event.setCanceled(true);
                return;
            }
            return;
        }
        if ("menu.disconnect".equals(key)) {
            if (ClientConfig.confirmTypeQuitMultiplayer.get() == ClientConfig.ConfirmType.TOAST) {
                if (toastInMultiplayerQuitHandle.trigger()) event.setCanceled(true);
                return;
            }
            if (ClientConfig.confirmTypeQuitMultiplayer.get() == ClientConfig.ConfirmType.SCREEN) {
                Minecraft.getInstance().setScreen(new ConfirmScreen(Minecraft.getInstance().screen, Component.translatable("screen.quitornot.confirm.title.multiplayer"), button::onPress));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onClientScheduleStop(ClientScheduleStopEvent event) {
        if (ClientConfig.confirmTypeQuitGame.get() == ClientConfig.ConfirmType.TOAST) {
            if (toastInFinalQuitHandler.trigger()) event.setCanceled(true);
            return;
        }
        if (ClientConfig.confirmTypeQuitGame.get() == ClientConfig.ConfirmType.SCREEN) {
            Minecraft.getInstance().setScreen(new ConfirmScreen(Minecraft.getInstance().screen, Component.translatable("screen.quitornot.confirm.title.game"), () -> Minecraft.getInstance().stop()));
            event.setCanceled(true);
        }
    }
}
