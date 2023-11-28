package com.plr.quitornot.client.event.impl;

import com.plr.quitornot.client.event.api.CancelableEvent;
import net.minecraft.client.gui.components.Button;

public class ButtonPressEvent extends CancelableEvent {
    private final Button button;

    public ButtonPressEvent(Button button) {
        this.button = button;
    }


    public Button getButton() {
        return button;
    }
}
