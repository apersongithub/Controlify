/*? if sodium {*/
package dev.isxander.controlify.compatibility.sodium.mixins;

import dev.isxander.controlify.compatibility.sodium.screenop.SodiumGuiScreenProcessor;
import dev.isxander.controlify.compatibility.sodium.screenop.SodiumScreenOperations;
import dev.isxander.controlify.screenop.ScreenProcessor;
import dev.isxander.controlify.screenop.ScreenProcessorProvider;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.List;

import net.caffeinemc.mods.sodium.client.config.structure.Page;
import net.caffeinemc.mods.sodium.client.gui.VideoSettingsScreen;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlElement;
import net.caffeinemc.mods.sodium.client.gui.widgets.FlatButtonWidget;
import net.caffeinemc.mods.sodium.client.gui.widgets.OptionListWidget;

@Mixin(VideoSettingsScreen.class)
public abstract class SodiumOptionsGUIMixin extends Screen implements ScreenProcessorProvider, SodiumScreenOperations {
    @Shadow private OptionListWidget optionList;

    @Shadow
    public abstract void jumpToPage(Page page);

    @Shadow
    private FlatButtonWidget applyButton;
    @Shadow
    private FlatButtonWidget closeButton;
    @Shadow
    private FlatButtonWidget undoButton;
    @Unique private final SodiumGuiScreenProcessor controlify$screenProcessor
            = new SodiumGuiScreenProcessor(this, this);

    protected SodiumOptionsGUIMixin(Component title) {
        super(title);
    }

    @Inject(method = "rebuild", at = @At("RETURN"))
    private void afterRebuild(CallbackInfo ci) {
        List<ControlElement> controls = this.optionList.getControls();
        if (!controls.isEmpty()) {
            this.setInitialFocus(controls.get(0));
        }
        controlify$screenProcessor.onRebuildGUI();
    }

    @Override
    public ScreenProcessor<?> screenProcessor() {
        return controlify$screenProcessor;
    }

    @Override
    public void controlify$nextPage() {
        controlify$changePage(1);
    }

    @Override
    public void controlify$prevPage() {
        controlify$changePage(-1);
    }

    @Unique
    private void controlify$changePage(int offset) {
        var accessor = (OptionListWidgetAccessor) this.optionList;
        var sections = accessor.controlify$getPageToSectionInfo();
        if (sections.isEmpty()) {
            return;
        }

        List<Page> pages = sections.reference2ReferenceEntrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> ((OptionListWidgetSectionAccessor) entry.getValue()).controlify$scrollJumpTarget()))
                .map(entry -> entry.getKey())
                .toList();

        var currentSection = accessor.controlify$getLastFocusedSection();
        int currentIndex = 0;
        if (currentSection != null) {
            for (int i = 0; i < pages.size(); i++) {
                var section = sections.get(pages.get(i));
                if (section == currentSection || section.equals(currentSection)) {
                    currentIndex = i;
                    break;
                }
            }
        }

        int nextIndex = Math.floorMod(currentIndex + offset, pages.size());
        this.jumpToPage(pages.get(nextIndex));
    }

    @Override
    public FlatButtonWidget controlify$getApplyButton() {
        return applyButton;
    }

    @Override
    public FlatButtonWidget controlify$getCloseButton() {
        return closeButton;
    }

    @Override
    public FlatButtonWidget controlify$getUndoButton() {
        return undoButton;
    }
}
/*?}*/
