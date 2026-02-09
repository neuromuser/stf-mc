package com.neuromuser.shittofit;

import com.neuromuser.shittofit.components.ModComponents;
import com.neuromuser.shittofit.components.PlayerDataComponent;
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
                ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
                        ServerPlayerEntity player = handler.player;

                        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(player);

                        if (!data.isInitialized()){
                                data.setInitialized(true);
                                data.setMaxHealth(0.5f);
                                PlayerStatManager.setPlayerHealth(player, 0.5f);
                        }
                        else PlayerStatManager.setPlayerHealth(player, data.getMaxHealth());
                }));

                ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
                        PlayerDataComponent data = ModComponents.PLAYER_DATA.get(newPlayer);
                        float newMax = data.getMaxHealth() + 1.0f;
                        data.setMaxHealth(newMax);
                        PlayerStatManager.setPlayerHealth(newPlayer, data.getMaxHealth());
                });
        }
}