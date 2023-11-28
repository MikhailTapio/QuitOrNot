package com.plr.quitornot.client.handler;

import com.plr.quitornot.client.config.Config;
import com.plr.quitornot.client.event.EventResult;
import com.plr.quitornot.client.toast.QuitToast;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ToastQuitHandler {
    private final Component message;
    private State state = State.INACTIVE;
    private long startTime = 0;

    public ToastQuitHandler(Component message) {
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
            Minecraft.getInstance().getToasts().addToast(
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
