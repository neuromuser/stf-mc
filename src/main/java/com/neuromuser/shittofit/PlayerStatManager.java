package com.neuromuser.shittofit;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class PlayerStatManager {

    public static void setPlayerHealth(ServerPlayerEntity player, float maxHealth){
        EntityAttributeInstance attribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (attribute != null){
            UUID modifierUuid = ModConstants.MAX_HEALTH_MODIFIER_UUID;
            attribute.removeModifier(modifierUuid);

            EntityAttributeModifier modifier = new EntityAttributeModifier(
                    modifierUuid,
                    (ShitToFit.MOD_ID + ":max_health"),
                    maxHealth - 20.0,
                    EntityAttributeModifier.Operation.ADDITION
            );

            attribute.addPersistentModifier(modifier);

            if (player.getHealth() > maxHealth) {
                player.setHealth(maxHealth);
            }
        }
    }
}
