package com.plr.quitornot.client.event.impl;

import net.minecraftforge.eventbus.api.Event;

public class ClientScheduleStopEvent extends Event {
    @Override
    public boolean isCancelable() {
        return true;
    }
}
