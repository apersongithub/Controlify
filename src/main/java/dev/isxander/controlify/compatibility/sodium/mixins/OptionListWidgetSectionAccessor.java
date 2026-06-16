/*? if sodium {*/
package dev.isxander.controlify.compatibility.sodium.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(targets = "net.caffeinemc.mods.sodium.client.gui.widgets.OptionListWidget$SectionInfo")
public interface OptionListWidgetSectionAccessor {
    @Invoker("scrollJumpTarget")
    int controlify$scrollJumpTarget();
}
/*?}*/
