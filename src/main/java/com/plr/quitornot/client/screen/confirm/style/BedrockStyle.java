package com.plr.quitornot.client.screen.confirm.style;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public final class BedrockStyle extends BaseStyle {
    private static final ResourceLocation WINDOW_TEXTURE = new ResourceLocation("quitornot", "textures/gui/bedrock/window.png");
    private static final ResourceLocation BACKGROUND = new ResourceLocation("textures/block/dirt.png");

    // 窗口宽度
    private static final int windowWidth = 252;
    // 窗口长度
    private static final int windowHeight = 140;
    // 按钮旁边距
    private static final int buttonLRMargin = 20;
    // 按钮下边距
    private static final int buttonBMargin = 20;
    // 按钮宽度
    private static final int buttonWidth = 100;
    // 按钮长度
    private static final int buttonHeight = 20;
    // 信息文本下边距
    private static final int messageBMargin = 100;

    @Override
    public Button generateConfirmButtons(Screen screen, Button.OnPress onConfirm) {
        return Button.builder(CommonComponents.GUI_YES, onConfirm)
                .bounds((screen.width - windowWidth) / 2 + windowWidth - buttonWidth - buttonLRMargin,
                        (screen.height - windowHeight) / 2 + windowHeight - buttonBMargin - buttonHeight,
                        buttonWidth, buttonHeight).build();
    }

    @Override
    public Button generateCancelButtons(Screen screen, Button.OnPress onCancel) {
        return Button.builder(CommonComponents.GUI_NO, onCancel)
                .bounds((screen.width - windowWidth) / 2 + buttonLRMargin,
                        (screen.height - windowHeight) / 2 + windowHeight - buttonBMargin - buttonHeight,
                        buttonWidth, buttonHeight).build();
    }

    @Override
    public void render(Minecraft client, Font textRenderer, Screen screen, Component title, Component message, GuiGraphics ctx, int mouseX, int mouseY, float delta) {
        drawBackground(client, screen, ctx);
        drawWindow(textRenderer, title, ctx, (screen.width - windowWidth) / 2, (screen.height - windowHeight) / 2);
        drawMessage(textRenderer, screen, message, ctx);
    }

    private void drawBackground(Minecraft client, Screen screen, GuiGraphics ctx) {
        if (client.level != null) {
            ctx.fillGradient(0, 0, screen.width, screen.height, -1072689136, -804253680);
        } else {
            screen.renderBackground(ctx);
            ctx.fill(0, 0, screen.width, screen.height, new Color(16, 16, 16, 179).getRGB());
        }
    }

    private void drawWindow(Font textRenderer, Component title, GuiGraphics ctx, int x, int y) {
        renderBackground(x, y, x + windowWidth, y + windowHeight, BACKGROUND);
        ctx.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        ctx.blit(WINDOW_TEXTURE, x, y, 0, 0, 252, 140);
        ctx.drawString(textRenderer, title, x + 8, y + 6, 4210752, false); // TODO: Shadow false?
    }


    private void drawMessage(Font textRenderer, Screen screen, Component message, GuiGraphics ctx) {
        ctx.drawCenteredString(textRenderer, message,
                screen.width / 2,
                (screen.height - windowHeight) / 2 + windowHeight - messageBMargin,
                10526880);
    }
}