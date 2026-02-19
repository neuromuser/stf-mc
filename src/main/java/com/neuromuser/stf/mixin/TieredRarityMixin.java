package com.neuromuser.stf.mixin;

import com.neuromuser.stf.components.ModComponents;
import com.neuromuser.stf.components.PlayerDataComponent;
import draylar.tiered.api.ModifierUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(value = ModifierUtils.class, priority = 10000)
public class TieredRarityMixin {
    @ModifyVariable(
            method = "getRandomAttributeIDFor",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/libz/util/SortList;concurrentSort(Ljava/util/List;[Ljava/util/List;)V",
                    ordinal = 0
            ),
            name = "attributeWeights")
    private static List<Integer> shitToFit$nerfRareModifiers(
            List<Integer> attributeWeights,
            @Nullable PlayerEntity playerEntity,
            Item item,
            boolean reforge
    ) {
        if (playerEntity == null) {
            return attributeWeights;
        }

        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(playerEntity);
        float modifier = data.getTieredzModifier();
        if (modifier >= 1.0f) {
            return attributeWeights;
        }
        int maxWeight = 0;
        for (int weight : attributeWeights) {
            if (weight > maxWeight) {
                maxWeight = weight;
            }
        }
        for (int i = 0; i < attributeWeights.size(); i++) {
            int currentWeight = attributeWeights.get(i);

            if (currentWeight > maxWeight / 3) {
                int nerfedWeight = Math.max(1, (int)(currentWeight / modifier));
                attributeWeights.set(i, nerfedWeight);
            }
        }

        return attributeWeights;
    }
}