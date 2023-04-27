package moe.caa.fabric.quitconfirm.client.toast;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class QuitToast extends BaseToast {
    public QuitToast(String message, long keepTime) {
        super("退出提醒", message, keepTime);
    }

    @Override
    protected void drawToast(MatrixStack matrices, ToastManager manager) {
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        DrawableHelper.drawTexture(matrices, 0, 0, 0, 0, 160, 32);
        ;
        DrawableHelper.drawTexture(matrices, 8, 0, 241, 0, 15, 30);
        ;
        manager.getClient().textRenderer.draw(matrices, title, 35.0f, 7.0f, Color.WHITE.getRed());
        manager.getClient().textRenderer.draw(matrices, message, 35.0f, 18.0f, Color.WHITE.getRed());
    }
}
