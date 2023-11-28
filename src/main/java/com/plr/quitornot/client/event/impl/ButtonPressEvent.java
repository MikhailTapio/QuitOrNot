package com.plr.quitornot.client.event.impl;

import net.minecraft.client.gui.components.Button;
import net.minecraftforge.eventbus.api.Event;

public class ButtonPressEvent extends Event {
    private final Button button;

    public ButtonPressEvent(Button button) {
        this.button = button;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    public Button getButton() {
        return button;
    }
}
