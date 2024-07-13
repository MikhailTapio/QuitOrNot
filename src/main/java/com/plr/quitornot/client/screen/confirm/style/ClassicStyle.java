package com.plr.quitornot.client.screen.confirm.style;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public final class ClassicStyle extends BaseStyle {
    // 按钮宽度
    private static final int buttonWidth = 150;
    // 按钮长度
    private static final int buttonHeight = 20;
    // 按钮间隔
    private static final int buttonFMargin = 10;
    // 按钮下边距
    private static final int buttonBMargin = 40;
    // 标题上边距
    private static final int titleTMargin = 30;

    @Override
    public Button generateConfirmButtons(Screen screen, Button.OnPress onConfirm) {
        return Button.builder(CommonComponents.GUI_YES, onConfirm)
                .bounds(screen.width / 2 - buttonWidth - buttonFMargin,
                        screen.height - buttonHeight - buttonBMargin,
                        buttonWidth, buttonHeight).build();
    }

    @Override
    public Button generateCancelButtons(Screen screen, Button.OnPress onCancel) {
        return Button.builder(CommonComponents.GUI_NO, onCancel)
                .bounds(screen.width / 2 + buttonFMargin,
                        screen.height - buttonHeight - buttonBMargin,
                        buttonWidth, buttonHeight).build();
    }

    @Override
    public void render(Minecraft client, Font textRenderer, Screen screen, Component title, Component message, GuiGraphics ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx, screen);
        drawTextAndMessage(textRenderer, screen, title, message, ctx);
    }

    public void renderBackground(GuiGraphics ctx, Screen screen) {
        if (Minecraft.getInstance().level != null) {
            ctx.fillGradient(0, 0, screen.width, screen.height, -1072689136, -804253680);
        } else {
            screen.renderTransparentBackground(ctx);
        }
    }

    private void drawTextAndMessage(Font textRenderer, Screen screen, Component title, Component message, GuiGraphics ctx) {
        ctx.drawCenteredString(textRenderer, title,
                screen.width / 2,
                titleTMargin,
                16777215);
        ctx.drawCenteredString(textRenderer, message,
                screen.width / 2,
                screen.height / 2 - 30,
                10526880);
    }
}