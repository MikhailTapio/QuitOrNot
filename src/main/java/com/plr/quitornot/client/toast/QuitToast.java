package com.plr.quitornot.client.toast;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.Text;

import java.awt.*;

public final class QuitToast extends BaseToast {
    public QuitToast(Text message, long keepTime) {
        super(Text.translatable("toast.quitornot.confirm.title"), message, keepTime);
    }

    @Override
    public int getWidth() {
        return 241;
    }

    @Override
    protected void drawToast(DrawContext context, ToastManager manager) {
        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        context.drawTexture(texture, 0, 0, 0, 0, getWidth(), getHeight());
        context.drawTexture(texture, 8, 0, 241, 0, 15, 30);
        context.drawTextWithShadow(manager.getClient().textRenderer, title, 35, 7, Color.WHITE.getRGB());
        context.drawTextWithShadow(manager.getClient().textRenderer, message, 35, 18, Color.WHITE.getRGB());
    }
}
