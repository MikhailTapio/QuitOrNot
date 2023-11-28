package com.plr.quitornot.client.screen.confirm;

import com.plr.quitornot.client.config.ClientConfig;
import com.plr.quitornot.client.screen.confirm.style.BaseStyle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public final class ConfirmScreen extends Screen {
    private final Component message;
    private final Runnable onCancel;
    private final Runnable onConfirm;
    private final long openTime;
    private final BaseStyle style = ClientConfig.confirmScreenStyle.get().baseStyleSupplier.get();
    private Button cancel;
    private Button confirm;

    private boolean confirmed = false;

    public boolean isConfirmed() {
        return confirmed;
    }

    public ConfirmScreen(Screen parentScreen, Component message, Runnable confirm) {
        super(Component.translatable("screen.quitornot.confirm.title"));
        this.openTime = System.currentTimeMillis();
        this.message = message;
        this.onCancel = () -> this.minecraft.setScreen(parentScreen);
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
        this.addRenderableWidget(confirm);
        this.addRenderableWidget(cancel);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        style.render(this.minecraft, this.font, this, title, message, context, mouseX, mouseY, delta);
        for (Renderable renderable : this.renderables) {
            renderable.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (ClientConfig.enableKey.get() && keyCode == GLFW.GLFW_KEY_ENTER && isActive()) {
            onConfirm.run();
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return ClientConfig.enableKey.get() && isActive();
    }

    @Override
    public void onClose() {
        onCancel.run();
    }

    public boolean isActive() {
        return openTime + ClientConfig.buttonWaitTime.get() < System.currentTimeMillis();
    }
}