package com.neuromuser.stf.exercise;

import com.neuromuser.stf.PlayerStatManager;
import com.neuromuser.stf.components.ModComponents;
import com.neuromuser.stf.components.PlayerDataComponent;
import com.neuromuser.stf.config.CommonConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.Map;

public class ExerciseManager {

    public static void completeExercise(ServerPlayerEntity player, int baseXp, Map<StatType, Integer> expMap) {
        CommonConfig config = AutoConfig.getConfigHolder(CommonConfig.class).getConfig();
        float multiplier = config.globalExpMultiplier;

        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        player.getWorld().playSound(null, player.getBlockPos(),
                SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 0.5f, 1.5f);

        for (Map.Entry<StatType, Integer> entry : expMap.entrySet()) {
            if (entry.getValue() > 0) {
                int levelBefore = data.getStatLevel(entry.getKey());

                data.addStatExperience(entry.getKey(), Math.round(entry.getValue() * multiplier));

                if (data.getStatLevel(entry.getKey()) > levelBefore) {
                    applyStatModifier(player, entry.getKey(), data);
                }
            }
        }

        data.addExperiencePoints(Math.round(baseXp * multiplier));

    }

    private static void applyStatModifier(ServerPlayerEntity player, StatType statType, PlayerDataComponent data) {
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
    }

    public static void upgradeStat(ServerPlayerEntity player, StatType statType) {
        if (statType == null) return;

        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        CommonConfig config = AutoConfig.getConfigHolder(CommonConfig.class).getConfig();

        if (data.getAvailableLevelPoints() <= 0) return;
        if (data.getStatLevel(statType) >= config.maxStatLevel) return;

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

    public static boolean isStatAtMaxLevel(ServerPlayerEntity player, StatType statType) {
        CommonConfig config = AutoConfig.getConfigHolder(CommonConfig.class).getConfig();
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        return data.getStatLevel(statType) >= config.maxStatLevel;
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