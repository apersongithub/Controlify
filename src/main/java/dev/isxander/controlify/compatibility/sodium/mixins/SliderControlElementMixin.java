//? if sodium {
package dev.isxander.controlify.compatibility.sodium.mixins;

import dev.isxander.controlify.compatibility.sodium.screenop.SliderControlProcessor;
import dev.isxander.controlify.screenop.ComponentProcessor;
import dev.isxander.controlify.screenop.ComponentProcessorProvider;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.*;

import net.caffeinemc.mods.sodium.client.config.structure.IntegerOption;
import net.caffeinemc.mods.sodium.client.gui.ColorTheme;
import net.caffeinemc.mods.sodium.client.gui.options.control.AbstractOptionList;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlElement;
import net.caffeinemc.mods.sodium.client.util.Dim2i;

@Mixin(targets = "net.caffeinemc.mods.sodium.client.gui.options.control.SliderControl$SliderControlElement")
public abstract class SliderControlElementMixin extends ControlElement implements ComponentProcessorProvider {
    @Shadow @Final private IntegerOption option;

    @Shadow public abstract double getThumbPositionForValue(int value);
    @Shadow public abstract void setValue(double thumbPosition);

    @Unique private final ComponentProcessor controlify$componentProcessor
            = new SliderControlProcessor(this::incrementSlider);

    public SliderControlElementMixin(AbstractOptionList list, Dim2i dim, ColorTheme theme) {
        super(list, dim, theme);
    }

    @Override
    public ComponentProcessor componentProcessor() {
        return controlify$componentProcessor;
    }

    @Unique
    private void incrementSlider(boolean reverse) {
        var validator = this.option.getSteppedValidator();
        int value = this.option.getValidatedValue();
        int nextValue = Mth.clamp(value + (reverse ? -validator.step() : validator.step()), validator.min(), validator.max());
        this.setValue(this.getThumbPositionForValue(nextValue));
    }
}
//?}
