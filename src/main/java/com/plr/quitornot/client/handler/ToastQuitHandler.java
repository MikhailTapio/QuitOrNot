package com.plr.quitornot.client.handler;

import com.plr.quitornot.client.config.ClientConfig;
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

    public boolean trigger() {
        long millis = System.currentTimeMillis();
        if (state == State.ACTIVE) {
            if (startTime + ClientConfig.toastKeepTime.get() < millis) {
                state = State.INACTIVE;
            }
        }

        if (state == State.INACTIVE) {
            startTime = millis;
            state = State.ACTIVE;
            Minecraft.getInstance().getToasts().addToast(new QuitToast(message, ClientConfig.toastKeepTime.get()));
            return true;
        }
        return millis < ClientConfig.toastDelay.get() + startTime || millis > ClientConfig.toastKeepTime.get() + startTime;
    }

    private enum State {
        ACTIVE,
        INACTIVE
    }
}
