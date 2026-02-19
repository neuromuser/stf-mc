package com.neuromuser.stf.exercise;

import com.neuromuser.stf.PlayerStatManager;
import com.neuromuser.stf.components.ModComponents;
import com.neuromuser.stf.components.PlayerDataComponent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.Map;

public class ExerciseManager {

    public static void completeExercise(ServerPlayerEntity player, int baseXp, Map<StatType, Integer> expMap) {
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        for (Map.Entry<StatType, Integer> entry : expMap.entrySet()) {
            if (entry.getValue() > 0) {
                data.addStatExperience(entry.getKey(), entry.getValue());
            }
        }
        data.addExperiencePoints(baseXp);
        player.getWorld().playSound(null, player.getBlockPos(),
                SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 0.5f, 1.5f);
    }

    public static void upgradeStat(ServerPlayerEntity player, StatType statType) {
        if (statType == null) return;

        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);

        if (data.getAvailableLevelPoints() <= 0) return;

        data.addStatExperience(statType, 100);

        data.setAvailableLevelPoints(data.getAvailableLevelPoints() - 1);

        switch (statType) {
            case MAX_HEALTH -> PlayerStatManager.setPlayerMaxHealth(player, data.getMaxHealth());
            case EXHAUSTION -> PlayerStatManager.setPlayerExhaustionModifier(player, data.getExhaustionModifier());
            case MINING_SPEED -> PlayerStatManager.setPlayerMiningSpeedModifier(player, data.getMiningSpeedModifier());
            case CRAFTING_TIME -> PlayerStatManager.setPlayerCraftingTimeModifier(player, data.getCraftingTimeModifier());
            case SPEED -> PlayerStatManager.setPlayerSpeedModifier(player, data.getSpeedModifier());
            case BREATH -> PlayerStatManager.setPlayerBreathModifier(player, data.getBreathModifier());
            case DAMAGE -> PlayerStatManager.setPlayerDamageModifier(player, data.getDamageModifier());
            case ATTACK_SPEED -> PlayerStatManager.setPlayerAttackSpeedModifier(player, data.getAttackSpeedModifier());
            case RANGED_TIME -> PlayerStatManager.setPlayerRangedTimeModifier(player, data.getRangedTimeModifier());
            case TIEREDZ -> PlayerStatManager.setPlayerTieredzModifier(player, data.getTieredzModifier());
        }

        player.getWorld().playSound(null, player.getBlockPos(),
                SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 0.7f, 1.2f);
    }

    public enum StatType {
        MAX_HEALTH,
        EXHAUSTION,
        MINING_SPEED,
        CRAFTING_TIME,
        SPEED,
        BREATH,
        DAMAGE,
        ATTACK_SPEED,
        RANGED_TIME,
        TIEREDZ
    }
}