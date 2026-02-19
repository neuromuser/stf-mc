package com.neuromuser.stf.network;

import com.neuromuser.stf.SweatToFitMod;
import com.neuromuser.stf.components.ModComponents;
import com.neuromuser.stf.components.PlayerDataComponent;
import com.neuromuser.stf.exercise.ExerciseManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.Map;

public class NetworkHandler {
    public static final Identifier COMPLETE_EXERCISE = new Identifier(SweatToFitMod.MOD_ID, "complete_exercise");
    public static final Identifier UPGRADE_STAT = new Identifier(SweatToFitMod.MOD_ID, "upgrade_stat");
    public static final Identifier SYNC_PLAYER_DATA = new Identifier(SweatToFitMod.MOD_ID, "sync_player_data");

    public static void registerServerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(COMPLETE_EXERCISE, (server, player, handler, buf, responseSender) -> {
            int baseXp = buf.readInt();
            int count = buf.readInt();

            Map<ExerciseManager.StatType, Integer> expMap = new EnumMap<>(ExerciseManager.StatType.class);
            ExerciseManager.StatType[] statTypes = ExerciseManager.StatType.values();

            for (int i = 0; i < count; i++) {
                int statId = buf.readInt();
                int expAmount = buf.readInt();
                if (statId >= 0 && statId < statTypes.length && expAmount > 0) {
                    expMap.put(statTypes[statId], Math.min(expAmount, 2000));
                }
            }

            int safeBaseXp = Math.min(Math.max(baseXp, 0), 10000);
            Map<ExerciseManager.StatType, Integer> safeExpMap = expMap;

            server.execute(() -> {
                ExerciseManager.completeExercise(player, safeBaseXp, safeExpMap);
                sendPlayerDataSync(player);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(UPGRADE_STAT, (server, player, handler, buf, responseSender) -> {
            int statId = buf.readInt();
            server.execute(() -> {
                if (statId >= 0 && statId < ExerciseManager.StatType.values().length) {
                    ExerciseManager.StatType statType = ExerciseManager.StatType.values()[statId];
                    ExerciseManager.upgradeStat(player, statType);
                    sendPlayerDataSync(player);
                }
            });
        });
    }

    public static void sendPlayerDataSync(ServerPlayerEntity player) {
        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeInt(data.getExperiencePoints());
        buf.writeInt(data.getAvailableLevelPoints());
        buf.writeInt(data.getOverallLevel());

        for (ExerciseManager.StatType stat : ExerciseManager.StatType.values()) {
            buf.writeInt(data.getStatLevel(stat));
            buf.writeInt(data.getStatExperience(stat));
        }

        ServerPlayNetworking.send(player, SYNC_PLAYER_DATA, buf);
    }
}