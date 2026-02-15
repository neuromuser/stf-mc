package com.neuromuser.shittofit;

import com.neuromuser.shittofit.components.ModComponents;
import com.neuromuser.shittofit.components.PlayerDataComponent;
import com.neuromuser.shittofit.network.NetworkHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShitToFit implements ModInitializer {
        public static final String MOD_ID = "stf";
        public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

        @Override
        public void onInitialize() {
                NetworkHandler.registerServerPackets();

                ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
                        ServerPlayerEntity player = handler.player;
                        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);

                        if (!data.isInitialized()){
                                data.setInitialized(true);
                        }

                        initializePlayerStats(player);
                        ShitToFit.LOGGER.info("Player join - XP: {}, Level: {}, Points: {}",
                                data.getExperiencePoints(), data.getOverallLevel(), data.getAvailableLevelPoints());

                        NetworkHandler.sendPlayerDataSync(player);

                }));

                ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
                        initializePlayerStats(newPlayer);

                        NetworkHandler.sendPlayerDataSync(newPlayer);
                });
        }

        public void initializePlayerStats(ServerPlayerEntity player){
                PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);
                PlayerStatManager.setPlayerMaxHealth(player, data.getMaxHealth());
                PlayerStatManager.setPlayerExhaustionModifier(player, data.getExhaustionModifier());
                PlayerStatManager.setPlayerMiningSpeedModifier(player, data.getMiningSpeedModifier());
                PlayerStatManager.setPlayerCraftingTimeModifier(player, data.getCraftingTimeModifier());
                PlayerStatManager.setPlayerSpeedModifier(player, data.getSpeedModifier());
                PlayerStatManager.setPlayerDamageModifier(player, data.getDamageModifier());
                PlayerStatManager.setPlayerAttackSpeedModifier(player, data.getAttackSpeedModifier());
                PlayerStatManager.setPlayerRangedTimeModifier(player, data.getRangedTimeModifier());
                PlayerStatManager.setPlayerTieredzModifier(player, data.getTieredzModifier());
        }

}