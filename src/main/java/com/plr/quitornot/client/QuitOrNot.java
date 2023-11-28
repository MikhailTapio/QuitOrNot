package com.plr.quitornot.client;

import com.plr.quitornot.client.config.ClientConfig;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(QuitOrNot.MODID)
public class QuitOrNot {
    public static final String MODID = "quitornot";

    public QuitOrNot() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CFG);
    }
}
