package com.plr.quitornot.client.screen.confirm.style;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class BaseStyle {
    public abstract Button generateConfirmButtons(Screen screen, Button.OnPress onConfirm);

    public abstract Button generateCancelButtons(Screen screen, Button.OnPress onCancel);

    public abstract void render(
            Minecraft client, Font textRenderer, Screen screen, Component title, Component message,
            GuiGraphics ctx, int mouseX, int mouseY, float delta);

    protected void renderBackground(int startX, int startY, int endX, int endY, ResourceLocation identifier) {
        int width = endX - startX;
        int height = endY - startY;
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, identifier);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferBuilder.vertex(startX, endY, 0.0D).uv(0.0F, height / 32.0F).color(64, 64, 64, 255).endVertex();
        bufferBuilder.vertex(endX, endY, 0.0D).uv(width / 32.0F, height / 32.0F).color(64, 64, 64, 255).endVertex();
        bufferBuilder.vertex(endX, startY, 0.0D).uv(width / 32.0F, 0).color(64, 64, 64, 255).endVertex();
        bufferBuilder.vertex(startX, startY, 0.0D).uv(0.0F, 0).color(64, 64, 64, 255).endVertex();
        tesselator.end();
    }
}