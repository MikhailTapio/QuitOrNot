package com.plr.quitornot.client.integration.modmenu.screen;

import com.plr.quitornot.client.config.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import java.util.function.Consumer;

public final class SettingScreen extends Screen {
    private final Screen parentScreen;
    private Button back;

    public SettingScreen(Screen screen) {
        super(Component.translatable("config.quitornot.settings.title"));
        parentScreen = screen;
    }

    @Override
    protected void init() {

        back = Button.builder(CommonComponents.GUI_BACK, (button) -> {
            Config.save();
            minecraft.setScreen(parentScreen);
        }).bounds(this.width / 2 - 100, this.height - 30, 200, 20).build();


        SettingElementListWidget listWidget = new SettingElementListWidget(this.minecraft, this.width, this.height, 30 /* 上边距 */, height - 40/* 下边距 */, 24);
        {
            MutableComponent category = Component.translatable("config.quitornot.settings.type.confirm.category");
            listWidget.addEntry(listWidget.new CategoryEntry(category.setStyle(Style.EMPTY.withBold(true)), 11184810));

            listWidget.addEntry(listWidget.new ButtonListEntry(Button
                    .builder(Config.config.confirmTypeQuitGame.displayName, (button) -> {
                        Config.config.confirmTypeQuitGame = Config.config.nextEnum(Config.ConfirmType.class, Config.config.confirmTypeQuitGame);
                        button.setMessage((Config.config.confirmTypeQuitGame.displayName));
                    })
                    .bounds(0, 0, 50, 20).build(), Component.translatable("config.quitornot.settings.type.confirm.game")));

            listWidget.addEntry(listWidget.new ButtonListEntry(Button
                    .builder((Config.config.confirmTypeQuitSinglePlayer.displayName), (button) -> {
                        Config.config.confirmTypeQuitSinglePlayer = Config.config.nextEnum(Config.ConfirmType.class, Config.config.confirmTypeQuitSinglePlayer);
                        button.setMessage((Config.config.confirmTypeQuitSinglePlayer.displayName));
                    })
                    .bounds(0, 0, 50, 20).build(), Component.translatable("config.quitornot.settings.type.confirm.singleplayer")));

            listWidget.addEntry(listWidget.new ButtonListEntry(Button
                    .builder((Config.config.confirmTypeQuitMultiplayer.displayName), (button) -> {
                        Config.config.confirmTypeQuitMultiplayer = Config.config.nextEnum(Config.ConfirmType.class, Config.config.confirmTypeQuitMultiplayer);
                        button.setMessage((Config.config.confirmTypeQuitMultiplayer.displayName));
                    })
                    .bounds(0, 0, 50, 20).build(), Component.translatable("config.quitornot.settings.type.confirm.multiplayer")));
        }
        {
            MutableComponent category = Component.translatable("config.quitornot.settings.screen.category");
            listWidget.addEntry(listWidget.new CategoryEntry(category.setStyle(category.getStyle().withBold(true)), 11184810));

            listWidget.addEntry(listWidget.new ButtonListEntry(Button
                    .builder((Config.config.confirmScreenStyle.displayName), (button) -> {
                        Config.config.confirmScreenStyle = Config.config.nextEnum(Config.ScreenStyle.class, Config.config.confirmScreenStyle);
                        button.setMessage((Config.config.confirmScreenStyle.displayName));
                    })
                    .bounds(0, 0, 50, 20).build(),
                    Component.translatable("config.quitornot.settings.screen.style")));


            listWidget.addEntry(listWidget.new ButtonListEntry(Button
                    .builder(Config.config.enableKey ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF, (button) -> {
                        Config.config.enableKey = !Config.config.enableKey;
                        button.setMessage(Config.config.enableKey ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF);
                    })
                    .bounds(0, 0, 50, 20).build(), Component.translatable("config.quitornot.settings.screen.key")));

            EditBox keepDark = new EditBox(minecraft.font, 0, 0, 48, 20, Component.empty());
            keepDark.setValue(String.valueOf(Config.config.buttonWaitTime));
            keepDark.setResponder(new PositiveLongParser(keepDark, (it) -> Config.config.buttonWaitTime = it));
            listWidget.addEntry(listWidget.new InputListEntry(keepDark, Component.translatable("config.quitornot.settings.screen.wait")));
        }
        {
            MutableComponent category = Component.translatable("config.quitornot.settings.toast.category");
            listWidget.addEntry(listWidget.new CategoryEntry(category.setStyle(category.getStyle().withBold(true)), 11184810));

            EditBox toastDisplayTime = new EditBox(minecraft.font, 0, 0, 48, 20, Component.empty());
            toastDisplayTime.setValue(String.valueOf(Config.config.toastKeepTime));
            toastDisplayTime.setResponder(new PositiveLongParser(toastDisplayTime, (it) -> Config.config.toastKeepTime = it));
            listWidget.addEntry(listWidget.new InputListEntry(toastDisplayTime, Component.translatable("config.quitornot.settings.toast.time")));

            EditBox toastStartAliveTime = new EditBox(minecraft.font, 0, 0, 48, 20, Component.empty());
            toastStartAliveTime.setValue(String.valueOf(Config.config.toastDelay));
            toastStartAliveTime.setResponder(new PositiveLongParser(toastStartAliveTime, (it) -> Config.config.toastDelay = it));
            listWidget.addEntry(listWidget.new InputListEntry(toastStartAliveTime, Component.translatable("config.quitornot.settings.toast.start")));
        }
        this.addRenderableWidget(listWidget);
        this.addRenderableWidget(back);
    }

    @Override
    public void render(GuiGraphics ctx, int mouseX, int mouseY, float delta) {
        super.render(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);

        MutableComponent literal = Component.literal("QuitOrNot");
        ctx.drawString(font, literal, 2, this.height - font.lineHeight, 5592405);
    }

    @Override
    public void onClose() {
        Config.save();
        minecraft.setScreen(parentScreen);
    }

    class PositiveLongParser implements Consumer<String> {
        private final EditBox fieldWidget;
        private final Consumer<Long> longConsumer;

        PositiveLongParser(EditBox fieldWidget, Consumer<Long> longConsumer) {
            this.fieldWidget = fieldWidget;
            this.longConsumer = longConsumer;
        }

        @Override
        public void accept(String s) {
            try {
                long parsed = Long.parseLong(s);
                if (parsed >= 0) {
                    fieldWidget.setTextColor(14737632);
                    longConsumer.accept(parsed);
                    back.active = true;
                    return;
                }
            } catch (Exception ignored) {
            }
            back.active = false;
            fieldWidget.setTextColor(16711680);
        }
    }
}
