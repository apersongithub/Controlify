package dev.isxander.controlify.mixins.feature.screenop;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.isxander.controlify.screenop.ComponentProcessorProvider;
import dev.isxander.controlify.screenop.keyboard.KeyboardOverlayScreen;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Unique
    private Screen controlify$incomingScreen;

    @Inject(method = "setScreen", at = @At("HEAD"))
    private void captureIncomingScreen(Screen screen, CallbackInfo ci) {
        controlify$incomingScreen = screen;
    }

    @Inject(method = "setScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;updateTitle()V"))
    private void changeScreen(Screen screen, CallbackInfo ci) {
        ComponentProcessorProvider.REGISTRY.clearCache();
    }

    @WrapWithCondition(method = "setScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;removed()V"))
    private boolean preventRemovingOldScreen(Screen oldScreen) {
        return !(controlify$incomingScreen instanceof KeyboardOverlayScreen);
    }
}
