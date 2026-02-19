package com.neuromuser.stf.mixin;
import com.neuromuser.stf.components.ModComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sn2.crafttakestime.common.config.ContainerConfig;
import sn2.crafttakestime.common.core.CraftManager;
import sn2.crafttakestime.common.slot.SlotRange;

@Mixin(CraftManager.class)
public class CraftManagerMixin {
    @Inject(method = "getCraftingTime", at = @At("RETURN"), cancellable = true, remap = false)
    private void shittofit$applyPlayerModifier(int outputSlot, SlotRange ingredientSlots, ContainerConfig containerConfig, CallbackInfoReturnable<Float> cir) {
        float originalTime = cir.getReturnValue();
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player != null) {
            float multiplier = ModComponents.PLAYER_DATA.get(player).getCraftingTimeModifier();
            cir.setReturnValue(originalTime * multiplier);
        }
    }
}