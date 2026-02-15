package com.neuromuser.shittofit.mixin;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sn2.crafttakestime.sound.CraftingTickableSound;

@Mixin(value = CraftingTickableSound.class, remap = false)
public abstract class MixinCraftingTickableSound extends MovingSoundInstance {

    @Unique
    private int ticksExisted = 0;

    protected MixinCraftingTickableSound(SoundEvent sound, SoundCategory category, net.minecraft.util.math.random.Random random) {
        super(sound, category, random);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(BlockPos pos, CallbackInfo ci) {
        this.repeatDelay = 10;

        this.randomizePitch();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        this.ticksExisted++;

        if (this.ticksExisted % 10 == 0) {
            this.randomizePitch();
        }
    }

    @Unique
    private void randomizePitch() {
        this.pitch = 0.9F + (new java.util.Random().nextFloat() * 0.2F);
    }
}