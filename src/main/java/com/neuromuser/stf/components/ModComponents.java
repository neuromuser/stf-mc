package com.neuromuser.stf.components;

import com.neuromuser.stf.SweatToFitMod;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

public class ModComponents implements EntityComponentInitializer {
    public static ComponentKey<PlayerDataComponent> PLAYER_DATA;

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        PLAYER_DATA = ComponentRegistry.getOrCreate(
                new Identifier(SweatToFitMod.MOD_ID, "player_data"),
                PlayerDataComponent.class
        );

        registry.registerForPlayers(PLAYER_DATA, player -> new PlayerDataComponent(), RespawnCopyStrategy.ALWAYS_COPY);
    }

    public static void syncPlayerData(net.minecraft.entity.player.PlayerEntity player) {
        if (!player.getWorld().isClient) {
            PLAYER_DATA.sync(player);
        }
    }
}