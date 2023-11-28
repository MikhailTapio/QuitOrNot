package com.plr.quitornot.client;

import com.plr.quitornot.client.config.ClientConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(QuitOrNot.MODID)
public class QuitOrNot {
    public static final String MODID = "quitornot";

    public QuitOrNot() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CFG);
    }
}
