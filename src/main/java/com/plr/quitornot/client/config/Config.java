package com.plr.quitornot.client.config;

import com.google.gson.*;
import com.plr.quitornot.client.QuitOrNot;
import com.plr.quitornot.client.screen.confirm.style.BaseStyle;
import com.plr.quitornot.client.screen.confirm.style.BedrockStyle;
import com.plr.quitornot.client.screen.confirm.style.ClassicStyle;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Supplier;

public class Config {
    public static final Config config = new Config();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Path path = FabricLoader.getInstance().getConfigDir().resolve("quit-or-not.json");
    public ConfirmTypeEnum confirmTypeQuitGame = ConfirmTypeEnum.SCREEN;
    public ConfirmTypeEnum confirmTypeQuitSinglePlayer = ConfirmTypeEnum.TOAST;
    public ConfirmTypeEnum confirmTypeQuitMultiplayer = ConfirmTypeEnum.TOAST;
    public boolean enableKey = true;
    public long buttonWaitTime = 500L;
    public confirmScreenStyleEnum confirmScreenStyle = confirmScreenStyleEnum.BEDROCK;
    public long toastKeepTime = 5000L;
    public long toastDelay = 500L;

    public static void load() {
        try {
            if (Files.notExists(path)) {
                save();
                return;
            }
            JsonElement element = JsonParser.parseString(Files.readString(path));
            JsonObject object = element.getAsJsonObject();
            if (object.has("confirmTypeQuitGame")) {
                config.confirmTypeQuitGame = ConfirmTypeEnum.valueOf(object.getAsJsonPrimitive("confirmTypeQuitGame").getAsString());
            }
            if (object.has("confirmTypeQuitSinglePlayer")) {
                config.confirmTypeQuitSinglePlayer = ConfirmTypeEnum.valueOf(object.getAsJsonPrimitive("confirmTypeQuitSinglePlayer").getAsString());
            }
            if (object.has("confirmTypeQuitMultiplayer")) {
                config.confirmTypeQuitMultiplayer = ConfirmTypeEnum.valueOf(object.getAsJsonPrimitive("confirmTypeQuitMultiplayer").getAsString());
            }
            if (object.has("enableKey")) {
                config.enableKey = object.getAsJsonPrimitive("enableKey").getAsBoolean();
            }
            if (object.has("buttonWaitTime")) {
                config.buttonWaitTime = object.getAsJsonPrimitive("buttonWaitTime").getAsLong();
            }
            if (object.has("confirmScreenStyle")) {
                config.confirmScreenStyle = confirmScreenStyleEnum.valueOf(object.getAsJsonPrimitive("confirmScreenStyle").getAsString());
            }
            if (object.has("toastKeepTime")) {
                config.toastKeepTime = object.getAsJsonPrimitive("toastKeepTime").getAsLong();
            }
            if (object.has("toastDelay")) {
                config.toastDelay = object.getAsJsonPrimitive("toastDelay").getAsLong();
            }
        } catch (Exception e){
            QuitOrNot.LOGGER.error("Failed to read " + path, e);
            save();
        }
    }

    public static void save() {
        try {
            Files.writeString(path, gson.toJson(config), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            QuitOrNot.LOGGER.error("Failed to save " + path, e);
        }
    }

    public <T extends Enum<T>> T nextEnum(Class<T> tClass, T value) {
        int ordinal = value.ordinal();
        T[] constants = tClass.getEnumConstants();
        return constants[(ordinal + 1) % constants.length];
    }

    public enum confirmScreenStyleEnum {
        CLASSIC(Component.translatable("config.quitornot.type.screen.classic"), ClassicStyle::new),
        BEDROCK(Component.translatable("config.quitornot.type.screen.bedrock"), BedrockStyle::new);
        public final Component displayName;
        public final Supplier<BaseStyle> baseStyleSupplier;

        confirmScreenStyleEnum(Component displayName, Supplier<BaseStyle> baseStyleSupplier) {
            this.displayName = displayName;
            this.baseStyleSupplier = baseStyleSupplier;
        }
    }


    public enum ConfirmTypeEnum {
        TOAST(Component.translatable("config.quitornot.type.confirm.toast")),
        SCREEN(Component.translatable("config.quitornot.type.confirm.screen")),
        NONE(Component.translatable("config.quitornot.type.confirm.none"));
        public final Component displayName;

        ConfirmTypeEnum(Component displayName) {
            this.displayName = displayName;
        }
    }
}
