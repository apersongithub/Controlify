/*? if sodium {*/
package dev.isxander.controlify.compatibility.sodium.mixins;

import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import net.caffeinemc.mods.sodium.client.config.structure.Page;
import net.caffeinemc.mods.sodium.client.gui.widgets.OptionListWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.Coerce;

@Mixin(OptionListWidget.class)
public interface OptionListWidgetAccessor {
    @Accessor("pageToSectionInfo")
    Reference2ReferenceMap<Page, OptionListWidgetSectionAccessor> controlify$getPageToSectionInfo();

    @Coerce
    @Accessor("lastFocusedSection")
    OptionListWidgetSectionAccessor controlify$getLastFocusedSection();
}
/*?}*/
