package com.plr.quitornot.client;

import com.mojang.logging.LogUtils;
import com.plr.quitornot.client.config.Config;
import com.plr.quitornot.client.event.ButtonPressEvent;
import com.plr.quitornot.client.event.ClientScheduleStopEvent;
import com.plr.quitornot.client.event.EventResult;
import com.plr.quitornot.client.handler.ToastQuitHandler;
import com.plr.quitornot.client.screen.confirm.ConfirmScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.slf4j.Logger;


public class QuitOrNot implements ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    private final ToastQuitHandler toastInFinalQuitHandler = new ToastQuitHandler(Component.translatable("toast.quitornot.confirm.title.game"));
    private final ToastQuitHandler toastInSinglePlayerQuitHandle = new ToastQuitHandler(Component.translatable("toast.quitornot.confirm.title.singleplayer"));
    private final ToastQuitHandler toastInMultiplayerQuitHandle = new ToastQuitHandler(Component.translatable("toast.quitornot.confirm.title.multiplayer"));

    @Override
    public void onInitializeClient() {
        Config.load();
        ClientScheduleStopEvent.CLIENT_SCHEDULE_STOP.register(() -> {
            if (Config.config.confirmTypeQuitGame == Config.ConfirmTypeEnum.TOAST) {
                return toastInFinalQuitHandler.trigger();
            }
            if (Config.config.confirmTypeQuitGame == Config.ConfirmTypeEnum.SCREEN) {
                Minecraft.getInstance().setScreen(new ConfirmScreen(Minecraft.getInstance().screen, Component.translatable("screen.quitornot.confirm.title.game"), () -> Minecraft.getInstance().stop()));
                return EventResult.CANCEL;
            }
            return EventResult.PASS;
        });

        ButtonPressEvent.BUTTON_PRESS.register(button -> {
            if (!(Minecraft.getInstance().screen instanceof PauseScreen)) {
                return EventResult.PASS;
            }
            String key = null;
            if (button.getMessage() instanceof TranslatableContents) {
                key = ((TranslatableContents) button.getMessage()).getKey();
            } else if (button.getMessage().getContents() instanceof TranslatableContents) {
                key = ((TranslatableContents) button.getMessage().getContents()).getKey();
            }
            if ("menu.returnToMenu".equals(key)) {
                if (Config.config.confirmTypeQuitSinglePlayer == Config.ConfirmTypeEnum.TOAST) {
                    return toastInSinglePlayerQuitHandle.trigger();
                }
                if (Config.config.confirmTypeQuitSinglePlayer == Config.ConfirmTypeEnum.SCREEN) {
                    Minecraft.getInstance().setScreen(new ConfirmScreen(Minecraft.getInstance().screen, Component.translatable("screen.quitornot.confirm.title.singleplayer"), button::onPress));
                    return EventResult.CANCEL;
                }
                return EventResult.PASS;
            }
            if ("menu.disconnect".equals(key)) {
                if (Config.config.confirmTypeQuitMultiplayer == Config.ConfirmTypeEnum.TOAST) {
                    return toastInMultiplayerQuitHandle.trigger();
                }
                if (Config.config.confirmTypeQuitMultiplayer == Config.ConfirmTypeEnum.SCREEN) {
                    Minecraft.getInstance().setScreen(new ConfirmScreen(Minecraft.getInstance().screen, Component.translatable("screen.quitornot.confirm.title.multiplayer"), button::onPress));
                    return EventResult.CANCEL;
                }
                return EventResult.PASS;
            }
            return EventResult.PASS;
        });
    }
}
