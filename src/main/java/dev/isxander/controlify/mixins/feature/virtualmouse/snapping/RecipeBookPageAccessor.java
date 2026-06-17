package dev.isxander.controlify.mixins.feature.virtualmouse.snapping;

import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import net.minecraft.client.gui.screens.recipebook.RecipeButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(RecipeBookPage.class)
public interface RecipeBookPageAccessor {
    @Accessor("buttons")
    List<RecipeButton> controlify$getButtons();

    //? if >=1.21.11 {
    @Accessor("forwardButton")
    net.minecraft.client.gui.components.ImageButton controlify$getForwardButton();

    @Accessor("backButton")
    net.minecraft.client.gui.components.ImageButton controlify$getBackButton();
    //?} else {
    /*@Accessor
    net.minecraft.client.gui.components.StateSwitchingButton controlify$getForwardButton();

    @Accessor
    net.minecraft.client.gui.components.StateSwitchingButton controlify$getBackButton();
    *///?}
}
