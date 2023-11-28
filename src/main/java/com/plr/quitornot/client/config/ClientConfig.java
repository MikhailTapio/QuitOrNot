package com.plr.quitornot.client.config;

import com.plr.quitornot.client.screen.confirm.style.BaseStyle;
import com.plr.quitornot.client.screen.confirm.style.BedrockStyle;
import com.plr.quitornot.client.screen.confirm.style.ClassicStyle;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Supplier;

public class ClientConfig {
    public static final ForgeConfigSpec CFG;
    public static final ForgeConfigSpec.ConfigValue<ConfirmType> confirmTypeQuitGame;
    public static final ForgeConfigSpec.ConfigValue<ConfirmType> confirmTypeQuitSinglePlayer;
    public static final ForgeConfigSpec.ConfigValue<ConfirmType> confirmTypeQuitMultiplayer;
    public static final ForgeConfigSpec.BooleanValue enableKey;
    public static final ForgeConfigSpec.IntValue buttonWaitTime;
    public static final ForgeConfigSpec.ConfigValue<ScreenStyle> confirmScreenStyle;
    public static final ForgeConfigSpec.IntValue toastKeepTime;
    public static final ForgeConfigSpec.IntValue toastDelay;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("QuitOrNot Settings").push("ConfirmType");
        confirmTypeQuitGame = builder.defineEnum("confirmTypeQuitGame", ConfirmType.SCREEN);
        confirmTypeQuitSinglePlayer = builder.defineEnum("confirmTypeQuitSinglePlayer", ConfirmType.TOAST);
        confirmTypeQuitMultiplayer = builder.defineEnum("confirmTypeQuitMultiplayer", ConfirmType.TOAST);
        builder.pop();
        builder.push("ConfirmScreen");
        enableKey = builder
                .comment("Enable using [Enter | Esc] to [confirm | close the screen]")
                .define("enableKey", true);
        buttonWaitTime = builder
                .comment("Only after milliseconds as many as below are the buttons on the screen active")
                .defineInRange("buttonWaitTime", 500, 0, 10000);
        confirmScreenStyle = builder.defineEnum("confirmScreenStyle", ScreenStyle.BEDROCK);
        builder.pop();
        builder.push("ConfirmToast");
        toastKeepTime = builder
                .comment("How long the Toast stays on the screen")
                .defineInRange("toastKeepTime", 5000, 1000, 20000);
        toastDelay = builder
                .comment("Only if milliseconds as many as below have passed since the Toast appeared will the actions work")
                .defineInRange("toastDelay", 500, 0, 5000);
        builder.pop();
        CFG = builder.build();
    }

    public enum ScreenStyle {
        CLASSIC(Component.translatable("config.quitornot.type.screen.classic"), ClassicStyle::new),
        BEDROCK(Component.translatable("config.quitornot.type.screen.bedrock"), BedrockStyle::new);
        public final Component displayName;
        public final Supplier<BaseStyle> baseStyleSupplier;

        ScreenStyle(Component displayName, Supplier<BaseStyle> baseStyleSupplier) {
            this.displayName = displayName;
            this.baseStyleSupplier = baseStyleSupplier;
        }
    }


    public enum ConfirmType {
        TOAST(Component.translatable("config.quitornot.type.confirm.toast")),
        SCREEN(Component.translatable("config.quitornot.type.confirm.screen")),
        NONE(Component.translatable("config.quitornot.type.confirm.none"));
        public final Component displayName;

        ConfirmType(Component displayName) {
            this.displayName = displayName;
        }
    }
}
