package com.neuromuser.shittofit.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;

public class PlayerDataComponent implements Component {
    private boolean initialized = false;
    private float maxHealth = 1.0f; //+ 20.0
    private float exhaustionModifier = 5.0f; //- 1.0
    private float miningSpeedModifier = 0.2f; //+ 1.0
    private float craftingTimeModifier = 4.0f; //- 1.0
    private float speedModifier = 0.5f; //+ 1.0
    private float breathModifier = 3.0f; //- 1.0
    private float damageModifier = 0.5f; //+ 1.0
    private float attackSpeedModifier = 0.4f; //+ 1.0
    private float rangedTimeModifier = 0.2f; //- 1.0
    private float tieredzModifier = 0.2f; //+ 1.0

    @Override
    public void readFromNbt(NbtCompound tag) {
        if (tag.contains("initialized")) {
            this.initialized = tag.getBoolean("initialized");
        }
        if (tag.contains("maxHealth")) {
            this.maxHealth = tag.getFloat("maxHealth");
        }
        if (tag.contains("exhaustionModifier")) {
            this.exhaustionModifier = tag.getFloat("exhaustionModifier");
        }
        if (tag.contains("miningSpeedModifier")) {
            this.miningSpeedModifier = tag.getFloat("miningSpeedModifier");
        }
        if (tag.contains("craftingTimeModifier")) {
            this.craftingTimeModifier = tag.getFloat("craftingTimeModifier");
        }
        if (tag.contains("speedModifier")) {
            this.speedModifier = tag.getFloat("speedModifier");
        }
        if (tag.contains("breathModifier")) {
            this.breathModifier = tag.getFloat("breathModifier");
        }
        if (tag.contains("damageModifier")) {
            this.damageModifier = tag.getFloat("damageModifier");
        }
        if (tag.contains("attackSpeedModifier")) {
            this.attackSpeedModifier = tag.getFloat("attackSpeedModifier");
        }
        if (tag.contains("rangedTimeModifier")) {
            this.rangedTimeModifier = tag.getFloat("rangedTimeModifier");
        }
        if (tag.contains("tieredzModifier")) {
            this.tieredzModifier = tag.getFloat("tieredzModifier");
        }
    }


    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("initialized", initialized);
        tag.putFloat("maxHealth", maxHealth);
        tag.putFloat("exhaustionModifier", exhaustionModifier);
        tag.putFloat("miningSpeedModifier", miningSpeedModifier);
        tag.putFloat("craftingTimeModifier", craftingTimeModifier);
        tag.putFloat("speedModifier", speedModifier);
        tag.putFloat("breathModifier", breathModifier);
        tag.putFloat("damageModifier", damageModifier);
        tag.putFloat("attackSpeedModifier", attackSpeedModifier);
        tag.putFloat("rangedTimeModifier", rangedTimeModifier);
        tag.putFloat("tieredzModifier", tieredzModifier);
    }

    public boolean isInitialized() { return initialized; }
    public void setInitialized(boolean bool) { initialized = bool; }

    public float getMaxHealth() { return maxHealth; }
    public void setMaxHealth(float value) { maxHealth = value; }

    public float getExhaustionModifier() { return exhaustionModifier; }
    public void setExhaustionModifier(float value) { exhaustionModifier = value; }

    public float getMiningSpeedModifier() { return miningSpeedModifier; }
    public void setMiningSpeedModifier(float value) { miningSpeedModifier = value; }

    public float getCraftingTimeModifier() { return craftingTimeModifier; }
    public void setCraftingTimeModifier(float value) { craftingTimeModifier = value; }

    public float getSpeedModifier() { return speedModifier; }
    public void setSpeedModifier(float value) { speedModifier = value; }

    public float getBreathModifier() { return breathModifier; }
    public void setBreathModifier(float value) { breathModifier = value; }

    public float getDamageModifier() { return damageModifier; }
    public void setDamageModifier(float value) { damageModifier = value; }

    public float getAttackSpeedModifier() { return attackSpeedModifier; }
    public void setAttackSpeedModifier(float value) { attackSpeedModifier = value; }

    public float getRangedTimeModifier() { return rangedTimeModifier; }
    public void setRangedTimeModifier(float value) { rangedTimeModifier = value; }

    public float getTieredzModifier() { return tieredzModifier; }
    public void setTieredzModifier(float value) { tieredzModifier = value; }
}
