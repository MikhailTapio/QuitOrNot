package com.plr.quitornot.client.screen.confirm;

import com.plr.quitornot.client.config.Config;
import com.plr.quitornot.client.screen.confirm.style.BaseStyle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class ConfirmScreen extends Screen {
    private final Text message;
    private final Runnable onCancel;
    private final Runnable onConfirm;
    private final long openTime;
    private final BaseStyle style = Config.config.confirmScreenStyle.baseStyleSupplier.get();
    private ButtonWidget cancel;
    private ButtonWidget confirm;

    private boolean confirmed = false;

    public boolean isConfirmed() {
        return confirmed;
    }

    public ConfirmScreen(Screen parentScreen, Text message, Runnable confirm) {
        super(Text.translatable("screen.quitornot.confirm.title"));
        this.openTime = System.currentTimeMillis();
        this.message = message;
        this.onCancel = () -> this.client.setScreen(parentScreen);
        this.onConfirm = () -> {
            confirmed = true;
            confirm.run();
        };
    }

    @Override
    protected void init() {
        initButton();
    }

    @Override
    public void tick() {
        if (isActive()) {
            cancel.active = true;
            confirm.active = true;
        }
    }

    private void initButton() {
        confirm = style.generateConfirmButtons(this, button -> {
            confirmed = true;
            onConfirm.run();
        });

        cancel = style.generateCancelButtons(this, button -> onCancel.run());

        confirm.active = false;
        cancel.active = false;
        this.addDrawableChild(confirm);
        this.addDrawableChild(cancel);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        style.render(this.client, this.textRenderer, this, title, message, context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (Config.config.enableKey && keyCode == GLFW.GLFW_KEY_ENTER && isActive()) {
            onConfirm.run();
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return Config.config.enableKey && isActive();
    }

    @Override
    public void close() {
        onCancel.run();
    }

    public boolean isActive() {
        return openTime + Config.config.buttonWaitTime < System.currentTimeMillis();
    }
}