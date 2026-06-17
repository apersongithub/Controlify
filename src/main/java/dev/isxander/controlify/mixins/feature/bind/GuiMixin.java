package dev.isxander.controlify.mixins.feature.bind;

import dev.isxander.controlify.api.ControlifyApi;
import dev.isxander.controlify.controller.ControllerEntity;
import dev.isxander.controlify.controller.input.InputComponent;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(method = "setScreen", at = @At("HEAD"))
    private void notifyBindGuiOutputOfScreenChange(CallbackInfo ci) {
        ControlifyApi.get().getCurrentController().flatMap(ControllerEntity::input)
                .ifPresent(InputComponent::notifyGuiPressOutputsOfNavigate);
    }
}
