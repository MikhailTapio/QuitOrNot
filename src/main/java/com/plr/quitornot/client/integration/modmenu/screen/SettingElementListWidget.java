package com.plr.quitornot.client.integration.modmenu.screen;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.Collections;
import java.util.List;

public final class SettingElementListWidget extends ContainerObjectSelectionList<SettingElementListWidget.Entry> {
    public SettingElementListWidget(Minecraft minecraftClient, int i, int j, int k, int l, int m) {
        super(minecraftClient, i, j, k, l, m);
    }

    @Override
    public int addEntry(Entry entry) {
        return super.addEntry(entry);
    }

    public class CategoryEntry extends Entry {
        private final Component text;
        private final int textWidth;
        private final int textColor;

        public CategoryEntry(Component text, int color) {
            this.text = text;
            this.textWidth = SettingElementListWidget.this.minecraft.font.width(text);
            this.textColor = color;
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(new NarratableEntry() {
                public NarrationPriority narrationPriority() {
                    return NarrationPriority.HOVERED;
                }

                public void updateNarration(NarrationElementOutput builder) {
                    builder.add(NarratedElementType.TITLE, text);
                }
            });
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return Collections.emptyList();
        }

        @Override
        public void render(GuiGraphics ctx, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            // 居中
            assert minecraft.screen != null;
            int dx = minecraft.screen.width / 2 - this.textWidth / 2;
            int dy = y + entryHeight;
            ctx.drawString(minecraft.font, this.text, dx, dy - minecraft.font.lineHeight, textColor, true);
        }
    }

    public class InputListEntry extends Entry {
        private final EditBox fieldWidget;
        private final Component describeText;

        public InputListEntry(EditBox fieldWidget, Component describeText) {
            this.fieldWidget = fieldWidget;
            this.describeText = describeText;
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return Collections.singletonList(fieldWidget);
        }

        @Override
        public void render(GuiGraphics ctx, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            ctx.drawString(minecraft.font, describeText, x, (int) (y + minecraft.font.lineHeight / 2.0),
                    16777215, true);
            this.fieldWidget.setX(entryWidth - fieldWidget.getWidth() + x);
            this.fieldWidget.setY(y);
            this.fieldWidget.render(ctx, mouseX, mouseY, tickDelta);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return Collections.singletonList(fieldWidget);
        }
    }


    public class ButtonListEntry extends Entry {
        private final Button button;
        private final Component describeText;

        public ButtonListEntry(Button settleButton, Component describeText) {
            this.button = settleButton;
            this.describeText = describeText;
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return Collections.singletonList(button);
        }

        @Override
        public void render(GuiGraphics ctx, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            ctx.drawString(minecraft.font, describeText, x, y + 5, 16777215, true);
            this.button.setX(entryWidth - button.getWidth() + x);
            this.button.setY(y);
            this.button.render(ctx, mouseX, mouseY, tickDelta);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return this.button.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return this.button.mouseReleased(mouseX, mouseY, button);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return Collections.singletonList(button);
        }
    }

    public abstract static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
    }
}
