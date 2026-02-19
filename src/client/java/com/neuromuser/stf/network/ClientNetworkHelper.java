package com.neuromuser.stf.network;

import com.neuromuser.stf.components.ModComponents;
import com.neuromuser.stf.components.PlayerDataComponent;
import com.neuromuser.stf.config.ClientConfig;
import com.neuromuser.stf.config.CommonConfig;
import com.neuromuser.stf.exercise.ExerciseManager;
import com.neuromuser.stf.ui.FitnessScreen;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

import java.util.Map;

public class ClientNetworkHelper {

    public static void registerClientPackets() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkHandler.SYNC_PLAYER_DATA, (client, handler, buf, responseSender) -> {
            int xp = buf.readInt();
            int points = buf.readInt();
            int level = buf.readInt();

            int[] statLevels = new int[ExerciseManager.StatType.values().length];
            int[] statExps = new int[ExerciseManager.StatType.values().length];

            for (int i = 0; i < ExerciseManager.StatType.values().length; i++) {
                statLevels[i] = buf.readInt();
                statExps[i] = buf.readInt();
            }

            client.execute(() -> {
                if (client.player != null) {
                    PlayerDataComponent data = ModComponents.PLAYER_DATA.get(client.player);
                    data.setExperiencePoints(xp);
                    data.setAvailableLevelPoints(points);
                    data.setOverallLevel(level);

                    ExerciseManager.StatType[] stats = ExerciseManager.StatType.values();
                    for (int i = 0; i < stats.length; i++) {
                        setStatLevel(data, stats[i], statLevels[i]);
                        setStatExp(data, stats[i], statExps[i]);
                    }

                    if (client.currentScreen instanceof FitnessScreen screen) {
                        screen.updateUI();
                    }
                }
            });
        });
    }

    private static void setStatLevel(PlayerDataComponent data, ExerciseManager.StatType stat, int level) {
        switch (stat) {
            case MAX_HEALTH -> data.setMaxHealthLevel(level);
            case EXHAUSTION -> data.setExhaustionLevel(level);
            case MINING_SPEED -> data.setMiningSpeedLevel(level);
            case CRAFTING_TIME -> data.setCraftingTimeLevel(level);
            case SPEED -> data.setSpeedLevel(level);
            case BREATH -> data.setBreathLevel(level);
            case DAMAGE -> data.setDamageLevel(level);
            case ATTACK_SPEED -> data.setAttackSpeedLevel(level);
            case RANGED_TIME -> data.setRangedTimeLevel(level);
            case TIEREDZ -> data.setTieredzLevel(level);
        }
    }

    private static void setStatExp(PlayerDataComponent data, ExerciseManager.StatType stat, int exp) {
        switch (stat) {
            case MAX_HEALTH -> data.setMaxHealthExp(exp);
            case EXHAUSTION -> data.setExhaustionExp(exp);
            case MINING_SPEED -> data.setMiningSpeedExp(exp);
            case CRAFTING_TIME -> data.setCraftingTimeExp(exp);
            case SPEED -> data.setSpeedExp(exp);
            case BREATH -> data.setBreathExp(exp);
            case DAMAGE -> data.setDamageExp(exp);
            case ATTACK_SPEED -> data.setAttackSpeedExp(exp);
            case RANGED_TIME -> data.setRangedTimeExp(exp);
            case TIEREDZ -> data.setTieredzExp(exp);
        }
    }

    public static void sendCompleteExercise(int exerciseIndex) {
        ClientConfig clientConfig = AutoConfig.getConfigHolder(ClientConfig.class).getConfig();
        CommonConfig commonConfig = AutoConfig.getConfigHolder(CommonConfig.class).getConfig();

        ClientConfig.ExerciseConfig exerciseConfig = clientConfig.getExercise(exerciseIndex);
        if (exerciseConfig == null || !exerciseConfig.enabled) return;

        float multiplier = commonConfig.globalExpMultiplier;
        Map<ExerciseManager.StatType, Integer> expMap = exerciseConfig.getExpMap();

        PacketByteBuf buf = PacketByteBufs.create();

        int baseXp = Math.round((10 + exerciseConfig.bonusBaseExp) * multiplier);
        buf.writeInt(baseXp);
        buf.writeInt(expMap.size());

        for (Map.Entry<ExerciseManager.StatType, Integer> entry : expMap.entrySet()) {
            buf.writeInt(entry.getKey().ordinal());
            buf.writeInt(Math.round(entry.getValue() * multiplier));
        }

        ClientPlayNetworking.send(NetworkHandler.COMPLETE_EXERCISE, buf);
    }

    public static void sendUpgradeStat(ExerciseManager.StatType statType) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(statType.ordinal());
        ClientPlayNetworking.send(NetworkHandler.UPGRADE_STAT, buf);
    }
}