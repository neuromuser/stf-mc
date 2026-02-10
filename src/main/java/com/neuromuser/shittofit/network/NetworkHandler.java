package com.neuromuser.shittofit.network;

import com.neuromuser.shittofit.ShitToFit;
import com.neuromuser.shittofit.components.ModComponents;
import com.neuromuser.shittofit.components.PlayerDataComponent;
import com.neuromuser.shittofit.exercise.ExerciseManager;
import com.neuromuser.shittofit.exercise.ExerciseType;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class NetworkHandler {
    public static final Identifier COMPLETE_EXERCISE = new Identifier(ShitToFit.MOD_ID, "complete_exercise");
    public static final Identifier UPGRADE_STAT = new Identifier(ShitToFit.MOD_ID, "upgrade_stat");
    public static final Identifier SYNC_PLAYER_DATA = new Identifier(ShitToFit.MOD_ID, "sync_player_data");

    public static void registerServerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(COMPLETE_EXERCISE, (server, player, handler, buf, responseSender) -> {
            int exerciseId = buf.readInt();
            server.execute(() -> {
                if (exerciseId >= 0 && exerciseId < ExerciseType.values().length) {
                    ExerciseType type = ExerciseType.values()[exerciseId];
                    ExerciseManager.completeExercise(player, type);
                    sendPlayerDataSync(player);
                }
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