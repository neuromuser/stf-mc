package com.neuromuser.stf;

import com.neuromuser.stf.components.ModComponents;
import com.neuromuser.stf.components.PlayerDataComponent;
import com.neuromuser.stf.config.CommonConfig;
import com.neuromuser.stf.network.NetworkHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SweatToFitMod implements ModInitializer {
        public static final String MOD_ID = "stf";
        public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

        @Override
        public void onInitialize() {
                AutoConfig.register(CommonConfig.class, GsonConfigSerializer::new);

                NetworkHandler.registerServerPackets();

                ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
                        ServerPlayerEntity player = handler.player;
                        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);

                        if (!data.isInitialized()) {
                                data.setInitialized(true);
                        }

                        initializePlayerStats(player);
                        SweatToFitMod.LOGGER.info("Player join - XP: {}, Level: {}, Points: {}",
                                data.getExperiencePoints(), data.getOverallLevel(), data.getAvailableLevelPoints());

                        NetworkHandler.sendPlayerDataSync(player);
                }));

                ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
                        initializePlayerStats(newPlayer);
                        NetworkHandler.sendPlayerDataSync(newPlayer);
                });
        }

        public void initializePlayerStats(ServerPlayerEntity player) {
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