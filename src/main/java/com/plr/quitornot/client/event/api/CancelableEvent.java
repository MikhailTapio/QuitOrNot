package com.plr.quitornot.client.event.api;

import net.neoforged.bus.api.Event;

public abstract class CancelableEvent extends Event {
    private boolean canceled;

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
