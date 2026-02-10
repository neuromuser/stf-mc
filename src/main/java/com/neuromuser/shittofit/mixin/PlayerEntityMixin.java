package com.neuromuser.shittofit.mixin;

import com.neuromuser.shittofit.components.ModComponents;
import com.neuromuser.shittofit.components.PlayerDataComponent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow
    protected boolean isSubmergedInWater;

    @Unique
    private int originalDrawTime = -1;
    @Unique
    private int drawStartTick = -1;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    //EXHAUSTION
    @ModifyArgs(
            method = "addExhaustion",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;addExhaustion(F)V")
    )
    private void modifyExhaustionAmount(Args args) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);

        if (data != null && data.isInitialized()) {
            float originalExhaustion = args.get(0);
            float modifier = data.getExhaustionModifier();

            args.set(0, originalExhaustion * modifier);
        }
    }

    //MINING SPEED
    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    private void modifyMiningSpeed(BlockState block, CallbackInfoReturnable<Float> cir){
        PlayerEntity player = (PlayerEntity) (Object) this;
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        float originalSpeed = cir.getReturnValue();
        cir.setReturnValue(originalSpeed * data.getMiningSpeedModifier());

        if (!player.getWorld().isClient) {
            player.addExhaustion(0.002F * data.getExhaustionModifier());
        }
    }

    //MOVEMENT SPEED
    @Inject(method = "getMovementSpeed", at = @At("RETURN"), cancellable = true)
    private void modifyMovementSpeed(CallbackInfoReturnable<Float> cir){
        PlayerEntity player = (PlayerEntity) (Object) this;
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        float originalSpeed = cir.getReturnValue();
        cir.setReturnValue(originalSpeed * data.getSpeedModifier());
    }

    //BREATHING UNDERWATER
    @Inject(method = "tick", at = @At("TAIL"))
    private void fasterBreathLoss(CallbackInfo ci) {
        if (!this.getWorld().isClient && this.isSubmergedInWater) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
            float breathModifier = data.getBreathModifier();

            int currentAir = this.getAir();
            if (currentAir > 0) {
                int additionalDrain = (int)(breathModifier - 1.0f);
                this.setAir(currentAir - additionalDrain);
            }
        }
    }

    // DAMAGE
    @ModifyArg(
            method = "attack(Lnet/minecraft/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
            ),
            index = 1
    )
    private float modifyDamageArgument(float damage) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        return damage * data.getDamageModifier();
    }

    // ATTACK SPEED
    @Inject(method = "getAttackCooldownProgressPerTick", at = @At("RETURN"), cancellable = true)
    private void modifyAttackCooldown(CallbackInfoReturnable<Float> cir){
        float baseSpeed = cir.getReturnValue();
        PlayerEntity player = (PlayerEntity) (Object) this;
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        cir.setReturnValue(baseSpeed / data.getAttackSpeedModifier());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void modifyBowDrawTime(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        LivingEntityAccessor accessor = (LivingEntityAccessor) this;

        if (player.isUsingItem()) {
            ItemStack activeItem = player.getActiveItem();

            if (activeItem.getItem() instanceof BowItem || activeItem.getItem() instanceof CrossbowItem) {
                PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
                float modifier = data.getRangedTimeModifier();

                if (originalDrawTime == -1) {
                    originalDrawTime = activeItem.getMaxUseTime();
                    drawStartTick = player.age;
                }

                if (modifier != 1.0f) {
                    int elapsedTicks = player.age - drawStartTick;
                    int targetElapsed = (int)(elapsedTicks * modifier);
                    int adjustedTimeLeft = Math.max(0, originalDrawTime - targetElapsed);
                    accessor.setItemUseTimeLeft(adjustedTimeLeft);
                }
            } else {
                originalDrawTime = -1;
                drawStartTick = -1;
            }
        } else {
            originalDrawTime = -1;
            drawStartTick = -1;
        }
    }
}
