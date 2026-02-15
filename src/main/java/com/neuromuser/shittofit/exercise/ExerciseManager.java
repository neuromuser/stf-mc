package com.neuromuser.shittofit.exercise;

import com.neuromuser.shittofit.PlayerStatManager;
import com.neuromuser.shittofit.components.ModComponents;
import com.neuromuser.shittofit.components.PlayerDataComponent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class ExerciseManager {

    public static void completeExercise(ServerPlayerEntity player, ExerciseType type) {
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        switch (type) {
            case PULLUPS -> {
                data.addStatExperience(StatType.DAMAGE, 20);
                data.addStatExperience(StatType.MINING_SPEED, 15);
                data.addStatExperience(StatType.TIEREDZ, 10);
            }
            case PUSHUPS -> {
                data.addStatExperience(StatType.DAMAGE, 15);
                data.addStatExperience(StatType.ATTACK_SPEED, 10);
                data.addStatExperience(StatType.TIEREDZ, 10);
            }
            case BURPIES -> {
                data.addStatExperience(StatType.EXHAUSTION, 15);
                data.addStatExperience(StatType.MINING_SPEED, 20);
                data.addStatExperience(StatType.RANGED_TIME, 40);
            }
            case SQUATS -> {
                data.addStatExperience(StatType.MAX_HEALTH, 15);
                data.addStatExperience(StatType.SPEED, 10);
                data.addStatExperience(StatType.CRAFTING_TIME, 20);
            }
            case PRESS -> {
                data.addStatExperience(StatType.MAX_HEALTH, 5);
                data.addStatExperience(StatType.DAMAGE, 15);
                data.addStatExperience(StatType.EXHAUSTION, 15);
            }
            case DUMBBELLS -> {
                data.addStatExperience(StatType.ATTACK_SPEED, 15);
                data.addStatExperience(StatType.MINING_SPEED, 15);
                data.addStatExperience(StatType.CRAFTING_TIME, 10);
            }
            case PLANK -> {
                data.addStatExperience(StatType.EXHAUSTION, 40);
                data.addStatExperience(StatType.BREATH, 40);
            }
            case RUN_WALK -> {
                data.addStatExperience(StatType.SPEED, 100);
                data.addStatExperience(StatType.BREATH, 60);
                data.addStatExperience(StatType.TIEREDZ, 40);
                data.addExperiencePoints(90);
            }
        }

        data.addExperiencePoints(10);

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