package com.neuromuser.shittofit.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;

public class PlayerDataComponent implements Component {
    private boolean initialized = false;
    private float maxHealth = 0.5f;

    @Override
    public void readFromNbt(NbtCompound tag) {
        initialized = tag.getBoolean("initialized");
        maxHealth = tag.getFloat("maxHealth");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("initialized", initialized);
        tag.putFloat("maxHealth", maxHealth);
    }

    public boolean isInitialized() { return initialized; }
    public void setInitialized(boolean bool) { initialized = bool; }

    public float getMaxHealth() { return maxHealth; }
    public void setMaxHealth(float value) { maxHealth = value; }

}
