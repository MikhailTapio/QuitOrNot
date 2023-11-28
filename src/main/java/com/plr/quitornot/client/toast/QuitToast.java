package com.plr.quitornot.client.toast;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;

import java.awt.*;

public final class QuitToast extends BaseToast {
    public QuitToast(Component message, long keepTime) {
        super(Component.translatable("toast.quitornot.confirm.title"), message, keepTime);
    }

    @Override
    public int width() {
        return 241;
    }

    @Override
    protected void drawToast(GuiGraphics context, ToastComponent manager) {
        context.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        context.blit(texture, 0, 0, 0, 0, width(), height());
        context.blit(texture, 8, 0, 241, 0, 15, 30);
        context.drawString(manager.getMinecraft().font, title, 35, 7, Color.WHITE.getRGB());
        context.drawString(manager.getMinecraft().font, message, 35, 18, Color.WHITE.getRGB());
    }
}
