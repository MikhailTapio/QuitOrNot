package com.plr.quitornot.client.mixin;


import com.plr.quitornot.client.event.impl.ButtonPressEvent;
import com.plr.quitornot.client.screen.confirm.ConfirmScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Button.class)
public abstract class MixinButton extends AbstractButton {
    public MixinButton(int i, int j, int k, int l, Component component) {
        super(i, j, k, l, component);
    }

    @Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
    private void qon$inject$onPress(CallbackInfo ci) {
        if (Minecraft.getInstance().screen instanceof ConfirmScreen c) {
            if (c.isConfirmed()) {
                return;
            }
        }
        final Event event = new ButtonPressEvent((Button) (Object) this);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}
