package com.plr.quitornot.client;

import com.mojang.logging.LogUtils;
import com.plr.quitornot.client.config.Config;
import com.plr.quitornot.client.event.ButtonPressEvent;
import com.plr.quitornot.client.event.ClientScheduleStopEvent;
import com.plr.quitornot.client.event.EventResult;
import com.plr.quitornot.client.handler.ToastQuitHandler;
import com.plr.quitornot.client.screen.confirm.ConfirmScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.slf4j.Logger;


public class QuitOrNot implements ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    private final ToastQuitHandler toastInFinalQuitHandler = new ToastQuitHandler(Text.translatable("toast.quitornot.confirm.title.game"));
    private final ToastQuitHandler toastInSinglePlayerQuitHandle = new ToastQuitHandler(Text.translatable("toast.quitornot.confirm.title.singleplayer"));
    private final ToastQuitHandler toastInMultiplayerQuitHandle = new ToastQuitHandler(Text.translatable("toast.quitornot.confirm.title.multiplayer"));

    @Override
    public void onInitializeClient() {
        Config.load();
        ClientScheduleStopEvent.CLIENT_SCHEDULE_STOP.register(() -> {
            if (Config.config.confirmTypeQuitGame == Config.ConfirmTypeEnum.TOAST) {
                return toastInFinalQuitHandler.trigger();
            }
            if (Config.config.confirmTypeQuitGame == Config.ConfirmTypeEnum.SCREEN) {
                MinecraftClient.getInstance().setScreen(new ConfirmScreen(MinecraftClient.getInstance().currentScreen, Text.translatable("screen.quitornot.confirm.title.game"), () -> MinecraftClient.getInstance().scheduleStop()));
                return EventResult.CANCEL;
            }
            return EventResult.PASS;
        });

        ButtonPressEvent.BUTTON_PRESS.register((button) -> {
            if (!(MinecraftClient.getInstance().currentScreen instanceof GameMenuScreen)) {
                return EventResult.PASS;
            }
            String key = null;
            if (button.getMessage() instanceof TranslatableTextContent) {
                key = ((TranslatableTextContent) button.getMessage()).getKey();
            } else if (button.getMessage().getContent() instanceof TranslatableTextContent) {
                key = ((TranslatableTextContent) button.getMessage().getContent()).getKey();
            }
            if ("menu.returnToMenu".equals(key)) {
                if (Config.config.confirmTypeQuitSinglePlayer == Config.ConfirmTypeEnum.TOAST) {
                    return toastInSinglePlayerQuitHandle.trigger();
                }
                if (Config.config.confirmTypeQuitSinglePlayer == Config.ConfirmTypeEnum.SCREEN) {
                    MinecraftClient.getInstance().setScreen(new ConfirmScreen(MinecraftClient.getInstance().currentScreen, Text.translatable("screen.quitornot.confirm.title.singleplayer"), button::onPress));
                    return EventResult.CANCEL;
                }
                return EventResult.PASS;
            }
            if ("menu.disconnect".equals(key)) {
                if (Config.config.confirmTypeQuitMultiplayer == Config.ConfirmTypeEnum.TOAST) {
                    return toastInMultiplayerQuitHandle.trigger();
                }
                if (Config.config.confirmTypeQuitMultiplayer == Config.ConfirmTypeEnum.SCREEN) {
                    MinecraftClient.getInstance().setScreen(new ConfirmScreen(MinecraftClient.getInstance().currentScreen, Text.translatable("screen.quitornot.confirm.title.multiplayer"), button::onPress));
                    return EventResult.CANCEL;
                }
                return EventResult.PASS;
            }
            return EventResult.PASS;
        });
    }
}
