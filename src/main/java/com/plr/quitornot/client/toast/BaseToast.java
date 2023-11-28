package com.plr.quitornot.client.toast;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class BaseToast implements Toast {
    protected static final Identifier texture = new Identifier("quitornot", "textures/gui/toasts.png");
    protected final Text title;
    protected final Text message;
    protected final long keepTime;

    protected BaseToast(Text title, Text message, long keepTime) {
        this.title = title;
        this.message = message;
        this.keepTime = keepTime;
    }

    @Override
    public Visibility draw(DrawContext context, ToastManager manager, long startTime) {
        drawToast(context, manager);
        return startTime >= keepTime ? Visibility.HIDE : Visibility.SHOW;
    }

    protected abstract void drawToast(DrawContext context, ToastManager manager);
}
