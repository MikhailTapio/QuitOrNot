package com.plr.quitornot.client.integration.modmenu.screen;

import com.plr.quitornot.client.config.Config;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public final class SettingScreen extends Screen {
    private final Screen parentScreen;
    private ButtonWidget back;

    public SettingScreen(Screen screen) {
        super(Text.translatable("config.quitornot.settings.title"));
        parentScreen = screen;
    }

    @Override
    protected void init() {

        back = ButtonWidget.builder(ScreenTexts.BACK, (button) -> {
            Config.save();
            client.setScreen(parentScreen);
        }).dimensions(this.width / 2 - 100, this.height - 30, 200, 20).build();


        SettingElementListWidget listWidget = new SettingElementListWidget(this.client, this.width, this.height, 30 /* 上边距 */, height - 40/* 下边距 */, 24);
        {
            MutableText category = Text.translatable("config.quitornot.settings.type.confirm.category");
            listWidget.addEntry(listWidget.new CategoryEntry(category.setStyle(Style.EMPTY.withBold(true)), 11184810));

            listWidget.addEntry(listWidget.new ButtonListEntry(ButtonWidget
                    .builder(Config.config.confirmTypeQuitGame.displayName, (button) -> {
                        Config.config.confirmTypeQuitGame = Config.config.nextEnum(Config.ConfirmTypeEnum.class, Config.config.confirmTypeQuitGame);
                        button.setMessage((Config.config.confirmTypeQuitGame.displayName));
                    })
                    .dimensions(0, 0, 50, 20).build(), Text.translatable("config.quitornot.settings.type.confirm.game")));

            listWidget.addEntry(listWidget.new ButtonListEntry(ButtonWidget
                    .builder((Config.config.confirmTypeQuitSinglePlayer.displayName), (button) -> {
                        Config.config.confirmTypeQuitSinglePlayer = Config.config.nextEnum(Config.ConfirmTypeEnum.class, Config.config.confirmTypeQuitSinglePlayer);
                        button.setMessage((Config.config.confirmTypeQuitSinglePlayer.displayName));
                    })
                    .dimensions(0, 0, 50, 20).build(), Text.translatable("config.quitornot.settings.type.confirm.singleplayer")));

            listWidget.addEntry(listWidget.new ButtonListEntry(ButtonWidget
                    .builder((Config.config.confirmTypeQuitMultiplayer.displayName), (button) -> {
                        Config.config.confirmTypeQuitMultiplayer = Config.config.nextEnum(Config.ConfirmTypeEnum.class, Config.config.confirmTypeQuitMultiplayer);
                        button.setMessage((Config.config.confirmTypeQuitMultiplayer.displayName));
                    })
                    .dimensions(0, 0, 50, 20).build(), Text.translatable("config.quitornot.settings.type.confirm.multiplayer")));
        }
        {
            MutableText category = Text.translatable("config.quitornot.settings.screen.category");
            listWidget.addEntry(listWidget.new CategoryEntry(category.setStyle(category.getStyle().withBold(true)), 11184810));

            listWidget.addEntry(listWidget.new ButtonListEntry(ButtonWidget
                    .builder((Config.config.confirmScreenStyle.displayName), (button) -> {
                        Config.config.confirmScreenStyle = Config.config.nextEnum(Config.confirmScreenStyleEnum.class, Config.config.confirmScreenStyle);
                        button.setMessage((Config.config.confirmScreenStyle.displayName));
                    })
                    .dimensions(0, 0, 50, 20).build(),
                    Text.translatable("config.quitornot.settings.screen.style")));


            listWidget.addEntry(listWidget.new ButtonListEntry(ButtonWidget
                    .builder(Config.config.enableKey ? ScreenTexts.ON : ScreenTexts.OFF, (button) -> {
                        Config.config.enableKey = !Config.config.enableKey;
                        button.setMessage(Config.config.enableKey ? ScreenTexts.ON : ScreenTexts.OFF);
                    })
                    .dimensions(0, 0, 50, 20).build(), Text.translatable("config.quitornot.settings.screen.key")));

            TextFieldWidget keepDark = new TextFieldWidget(client.textRenderer, 0, 0, 48, 20, Text.empty());
            keepDark.setText(String.valueOf(Config.config.buttonWaitTime));
            keepDark.setChangedListener(new PositiveLongParser(keepDark, (it) -> Config.config.buttonWaitTime = it));
            listWidget.addEntry(listWidget.new InputListEntry(keepDark, Text.translatable("config.quitornot.settings.screen.wait")));
        }
        {
            MutableText category = Text.translatable("config.quitornot.settings.toast.category");
            listWidget.addEntry(listWidget.new CategoryEntry(category.setStyle(category.getStyle().withBold(true)), 11184810));

            TextFieldWidget toastDisplayTime = new TextFieldWidget(client.textRenderer, 0, 0, 48, 20, Text.empty());
            toastDisplayTime.setText(String.valueOf(Config.config.toastKeepTime));
            toastDisplayTime.setChangedListener(new PositiveLongParser(toastDisplayTime, (it) -> Config.config.toastKeepTime = it));
            listWidget.addEntry(listWidget.new InputListEntry(toastDisplayTime, Text.translatable("config.quitornot.settings.toast.time")));

            TextFieldWidget toastStartAliveTime = new TextFieldWidget(client.textRenderer, 0, 0, 48, 20, Text.empty());
            toastStartAliveTime.setText(String.valueOf(Config.config.toastDelay));
            toastStartAliveTime.setChangedListener(new PositiveLongParser(toastStartAliveTime, (it) -> Config.config.toastDelay = it));
            listWidget.addEntry(listWidget.new InputListEntry(toastStartAliveTime, Text.translatable("config.quitornot.settings.toast.start")));
        }
        this.addDrawableChild(listWidget);
        this.addDrawableChild(back);
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        super.render(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 16777215);

        MutableText literal = Text.literal("QuitOrNot");
        ctx.drawTextWithShadow(textRenderer, literal, 2, this.height - textRenderer.fontHeight, 5592405);
    }

    @Override
    public void close() {
        Config.save();
        client.setScreen(parentScreen);
    }

    class PositiveLongParser implements Consumer<String> {
        private final TextFieldWidget fieldWidget;
        private final Consumer<Long> longConsumer;

        PositiveLongParser(TextFieldWidget fieldWidget, Consumer<Long> longConsumer) {
            this.fieldWidget = fieldWidget;
            this.longConsumer = longConsumer;
        }

        @Override
        public void accept(String s) {
            try {
                long parsed = Long.parseLong(s);
                if (parsed >= 0) {
                    fieldWidget.setEditableColor(14737632);
                    longConsumer.accept(parsed);
                    back.active = true;
                    return;
                }
            } catch (Exception ignored) {
            }
            back.active = false;
            fieldWidget.setEditableColor(16711680);
        }
    }
}
