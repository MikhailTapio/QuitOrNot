package com.plr.quitornot.client.handler;

import com.plr.quitornot.client.config.Config;
import com.plr.quitornot.client.event.EventResult;
import com.plr.quitornot.client.toast.QuitToast;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ToastQuitHandler {
    private final Text message;
    private State state = State.INACTIVE;
    private long startTime = 0;

    public ToastQuitHandler(Text message) {
        this.message = message;
    }

    public EventResult trigger() {
        long millis = System.currentTimeMillis();
        if (state == State.ACTIVE) {
            if (startTime + Config.config.toastKeepTime < millis) {
                state = State.INACTIVE;
            }
        }

        if (state == State.INACTIVE) {
            startTime = millis;
            state = State.ACTIVE;
            MinecraftClient.getInstance().getToastManager().add(
                    new QuitToast(message, Config.config.toastKeepTime)
            );
            return EventResult.CANCEL;
        }
        if (millis < Config.config.toastDelay + startTime) return EventResult.CANCEL;
        if (millis > Config.config.toastKeepTime + startTime) return EventResult.CANCEL;
        return EventResult.PASS;
    }

    private enum State {
        ACTIVE,
        INACTIVE
    }
}
