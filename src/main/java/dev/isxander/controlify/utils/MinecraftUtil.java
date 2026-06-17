package dev.isxander.controlify.utils;

import dev.isxander.controlify.mixins.feature.bind.GuiAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public final class MinecraftUtil {
    private static final Minecraft minecraft = Minecraft.getInstance();

    private MinecraftUtil() {
    }

    public static void setScreen(@Nullable Screen screen) {
        //? if >=26.2 {
        minecraft.gui.setScreen(screen);
        //?} else {
        /*dev.isxander.controlify.utils.MinecraftUtil.setScreen(screen);
        *///?}
    }

    public static void forceSetScreen(@Nullable Screen screen) {
        //? if >=26.2 {
        ((GuiAccessor) minecraft.gui).controlify$setScreenField(screen);
        //?} else {
        /*dev.isxander.controlify.utils.MinecraftUtil.getScreen() = screen;
        *///?}
    }

    @Contract(pure = true)
    public static @Nullable Screen getScreen() {
        //? if >=26.2 {
        return minecraft.gui.screen();
        //?} else {
        /*return dev.isxander.controlify.utils.MinecraftUtil.getScreen();
        *///?}
    }

    @Contract(pure = true)
    public static @Nullable Overlay getOverlay() {
        //? if >=26.2 {
        return minecraft.gui.overlay();
        //?} else {
        /*return dev.isxander.controlify.utils.MinecraftUtil.getOverlay();
        *///?}
    }

    public static void sendToast(Component title, Component message, boolean longer) {
        var toastId = longer ? SystemToast.SystemToastId.UNSECURE_SERVER_WARNING : SystemToast.SystemToastId.PERIODIC_NOTIFICATION;

        //? if >=26.2 {
        SystemToast.add(minecraft.gui.toastManager(), toastId, title, message);
        //?} else {
        /*SystemToast toast = SystemToast.multiline(minecraft, toastId, title, message);
        minecraft.getToastManager().addToast(toast);
        *///?}
    }
}
