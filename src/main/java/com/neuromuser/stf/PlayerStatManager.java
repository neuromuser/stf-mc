package com.neuromuser.stf;

import com.neuromuser.stf.components.ModComponents;
import com.neuromuser.stf.components.PlayerDataComponent;
import com.neuromuser.stf.config.CommonConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class PlayerStatManager {

    public static void setPlayerMaxHealth(ServerPlayerEntity player, float maxHealthFromLevels) {
        EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (attribute != null) {
            CommonConfig config = AutoConfig.getConfigHolder(CommonConfig.class).getConfig();
            float base = config.baseMaxHealth;

            UUID modifierUuid = ModConstants.MAX_HEALTH_MODIFIER_UUID;
            attribute.removeModifier(modifierUuid);

            EntityAttributeModifier modifier = new EntityAttributeModifier(
                    modifierUuid,
                    (SweatToFitMod.MOD_ID + ":max_health"),
                    (base + maxHealthFromLevels) - 20.0,
                    EntityAttributeModifier.Operation.ADDITION
            );

            attribute.addPersistentModifier(modifier);

            float actualMax = base + maxHealthFromLevels;
            if (player.getHealth() > actualMax) {
                player.setHealth(actualMax);
            }
        }
    }

    public static void setPlayerExhaustionModifier(ServerPlayerEntity player, float modifier) {
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        data.setExhaustionModifier(modifier);
    }

    public static void setPlayerMiningSpeedModifier(ServerPlayerEntity player, float modifier) {
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        data.setMiningSpeedModifier(modifier);
    }

    public static void setPlayerCraftingTimeModifier(ServerPlayerEntity player, float modifier) {
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        data.setCraftingTimeModifier(modifier);
    }

    public static void setPlayerSpeedModifier(ServerPlayerEntity player, float modifier) {
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        data.setSpeedModifier(modifier);
    }

    public static void setPlayerBreathModifier(ServerPlayerEntity player, float modifier) {
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        data.setBreathModifier(modifier);
    }

    public static void setPlayerDamageModifier(ServerPlayerEntity player, float modifier) {
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        data.setDamageModifier(modifier);
    }

    public static void setPlayerAttackSpeedModifier(ServerPlayerEntity player, float modifier) {
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        data.setAttackSpeedModifier(modifier);

        EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
        if (attribute != null) {
            UUID modifierUuid = ModConstants.ATTACK_SPEED_MODIFIER_UUID;

            attribute.removeModifier(modifierUuid);

            if (modifier != 1.0f) {
                EntityAttributeModifier attributeModifier = new EntityAttributeModifier(
                        modifierUuid,
                        SweatToFitMod.MOD_ID + ":attack_speed",
                        modifier - 1.0,
                        EntityAttributeModifier.Operation.MULTIPLY_TOTAL
                );
                attribute.addPersistentModifier(attributeModifier);
            }
        }
    }

    public static void setPlayerRangedTimeModifier(ServerPlayerEntity player, float modifier) {
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        data.setRangedTimeModifier(modifier);
    }

    public static void setPlayerTieredzModifier(ServerPlayerEntity player, float modifier) {
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        data.setTieredzModifier(modifier);
    }
}