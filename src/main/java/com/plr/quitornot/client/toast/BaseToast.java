package com.plr.quitornot.client.toast;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class BaseToast implements Toast {
    protected static final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("quitornot", "textures/gui/toasts.png");
    protected final Component title;
    protected final Component message;
    protected final long keepTime;

    protected BaseToast(Component title, Component message, long keepTime) {
        this.title = title;
        this.message = message;
        this.keepTime = keepTime;
    }

    @Override
    public Visibility render(GuiGraphics context, ToastComponent manager, long startTime) {
        drawToast(context, manager);
        return startTime >= keepTime ? Visibility.HIDE : Visibility.SHOW;
    }

    protected abstract void drawToast(GuiGraphics context, ToastComponent manager);
}
