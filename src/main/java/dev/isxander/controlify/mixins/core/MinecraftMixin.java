package dev.isxander.controlify.mixins.core;

import dev.isxander.controlify.Controlify;
import dev.isxander.controlify.controllermanager.ControllerManager;
import dev.isxander.controlify.utils.animation.impl.Animator;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    //? if >=1.21.2 {
    @Shadow public abstract net.minecraft.client.DeltaTracker getDeltaTracker();
    //?} elif >1.20.6 {
    /*@Shadow public abstract net.minecraft.client.DeltaTracker getTimer();

    @Unique
    public net.minecraft.client.DeltaTracker getDeltaTracker() {
        return getTimer();
    }
    *///?} else {
    /*@Shadow public abstract float getDeltaFrameTime();
    *///?}

    @Shadow
    public abstract void emergencySaveAndCrash(CrashReport crashReport);

    @Inject(method = "onGameLoadFinished", at = @At("RETURN"))
    private void initControlifyNow(CallbackInfo ci) {
        try {
            Controlify.instance().initializeControlify();
        } catch (Throwable t) {
            CrashReport report = CrashReport.forThrowable(t, "Failed to initialize Controlify");

            // Further up the stack, any throwable is caught, including ReportedException,
            // so we need to manually crash the game here.
            emergencySaveAndCrash(report);
        }
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MouseHandler;handleAccumulatedMovement()V"))
    private void doPlayerLook(boolean tick, CallbackInfo ci) {
        Controlify.instance().inGameInputHandler().ifPresent(ih -> ih.processPlayerLook(getTickDelta()));
    }

    @Inject(
            method = "close",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/telemetry/ClientTelemetryManager;close()V"
            )
    )
    private void onMinecraftClose(CallbackInfo ci) {
        Controlify.instance().getControllerManager().ifPresent(ControllerManager::close);
    }

    @Inject(
            method = "renderFrame",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V"
            )
    )
    private void tickAnimator(boolean tick, CallbackInfo ci) {
        Animator.INSTANCE.tick(getTickDelta());
    }

    @Unique
    private float getTickDelta() {
        return getDeltaTracker().getGameTimeDeltaTicks();
    }
}
