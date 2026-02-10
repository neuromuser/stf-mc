package com.neuromuser.shittofit.components;

import com.neuromuser.shittofit.exercise.ExerciseManager;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.NbtCompound;

import static java.lang.Math.floor;

public class PlayerDataComponent implements Component {
    private boolean initialized = false;

    private float maxHealth = 1.0f; 
    private float exhaustionModifier = 5.0f; 
    private float miningSpeedModifier = 0.2f; 
    private float craftingTimeModifier = 4.0f; 
    private float speedModifier = 0.5f; 
    private float breathModifier = 3.0f; 
    private float damageModifier = 0.5f; 
    private float attackSpeedModifier = 0.4f; 
    private float rangedTimeModifier = 0.2f; 
    private float tieredzModifier = 0.2f; 

    private int experiencePoints = 0;
    private int availableLevelPoints = 0;
    private int overallLevel = 1;

    private int maxHealthLevel = 0;
    private int exhaustionLevel = 0;
    private int miningSpeedLevel = 0;
    private int craftingTimeLevel = 0;
    private int speedLevel = 0;
    private int breathLevel = 0;
    private int damageLevel = 0;
    private int attackSpeedLevel = 0;
    private int rangedTimeLevel = 0;
    private int tieredzLevel = 0;

    private int maxHealthExp = 0;
    private int exhaustionExp = 0;
    private int miningSpeedExp = 0;
    private int craftingTimeExp = 0;
    private int speedExp = 0;
    private int breathExp = 0;
    private int damageExp = 0;
    private int attackSpeedExp = 0;
    private int rangedTimeExp = 0;
    private int tieredzExp = 0;

    private static final int BASE_EXP_REQUIREMENT = 100;
    private static final int MAX_STAT_LEVEL = 200;

    @Override
    public void readFromNbt(NbtCompound tag) {
        if (tag.contains("initialized")) this.initialized = tag.getBoolean("initialized");

        if (tag.contains("maxHealth")) this.maxHealth = tag.getFloat("maxHealth");
        if (tag.contains("exhaustionModifier")) this.exhaustionModifier = tag.getFloat("exhaustionModifier");
        if (tag.contains("miningSpeedModifier")) this.miningSpeedModifier = tag.getFloat("miningSpeedModifier");
        if (tag.contains("craftingTimeModifier")) this.craftingTimeModifier = tag.getFloat("craftingTimeModifier");
        if (tag.contains("speedModifier")) this.speedModifier = tag.getFloat("speedModifier");
        if (tag.contains("breathModifier")) this.breathModifier = tag.getFloat("breathModifier");
        if (tag.contains("damageModifier")) this.damageModifier = tag.getFloat("damageModifier");
        if (tag.contains("attackSpeedModifier")) this.attackSpeedModifier = tag.getFloat("attackSpeedModifier");
        if (tag.contains("rangedTimeModifier")) this.rangedTimeModifier = tag.getFloat("rangedTimeModifier");
        if (tag.contains("tieredzModifier")) this.tieredzModifier = tag.getFloat("tieredzModifier");

        if (tag.contains("experiencePoints")) this.experiencePoints = tag.getInt("experiencePoints");
        if (tag.contains("availableLevelPoints")) this.availableLevelPoints = tag.getInt("availableLevelPoints");
        if (tag.contains("overallLevel")) this.overallLevel = tag.getInt("overallLevel");

        if (tag.contains("maxHealthLevel")) this.maxHealthLevel = tag.getInt("maxHealthLevel");
        if (tag.contains("exhaustionLevel")) this.exhaustionLevel = tag.getInt("exhaustionLevel");
        if (tag.contains("miningSpeedLevel")) this.miningSpeedLevel = tag.getInt("miningSpeedLevel");
        if (tag.contains("craftingTimeLevel")) this.craftingTimeLevel = tag.getInt("craftingTimeLevel");
        if (tag.contains("speedLevel")) this.speedLevel = tag.getInt("speedLevel");
        if (tag.contains("breathLevel")) this.breathLevel = tag.getInt("breathLevel");
        if (tag.contains("damageLevel")) this.damageLevel = tag.getInt("damageLevel");
        if (tag.contains("attackSpeedLevel")) this.attackSpeedLevel = tag.getInt("attackSpeedLevel");
        if (tag.contains("rangedTimeLevel")) this.rangedTimeLevel = tag.getInt("rangedTimeLevel");
        if (tag.contains("tieredzLevel")) this.tieredzLevel = tag.getInt("tieredzLevel");

        if (tag.contains("maxHealthExp")) this.maxHealthExp = tag.getInt("maxHealthExp");
        if (tag.contains("exhaustionExp")) this.exhaustionExp = tag.getInt("exhaustionExp");
        if (tag.contains("miningSpeedExp")) this.miningSpeedExp = tag.getInt("miningSpeedExp");
        if (tag.contains("craftingTimeExp")) this.craftingTimeExp = tag.getInt("craftingTimeExp");
        if (tag.contains("speedExp")) this.speedExp = tag.getInt("speedExp");
        if (tag.contains("breathExp")) this.breathExp = tag.getInt("breathExp");
        if (tag.contains("damageExp")) this.damageExp = tag.getInt("damageExp");
        if (tag.contains("attackSpeedExp")) this.attackSpeedExp = tag.getInt("attackSpeedExp");
        if (tag.contains("rangedTimeExp")) this.rangedTimeExp = tag.getInt("rangedTimeExp");
        if (tag.contains("tieredzExp")) this.tieredzExp = tag.getInt("tieredzExp");
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

        tag.putInt("experiencePoints", experiencePoints);
        tag.putInt("availableLevelPoints", availableLevelPoints);
        tag.putInt("overallLevel", overallLevel);

        tag.putInt("maxHealthLevel", maxHealthLevel);
        tag.putInt("exhaustionLevel", exhaustionLevel);
        tag.putInt("miningSpeedLevel", miningSpeedLevel);
        tag.putInt("craftingTimeLevel", craftingTimeLevel);
        tag.putInt("speedLevel", speedLevel);
        tag.putInt("breathLevel", breathLevel);
        tag.putInt("damageLevel", damageLevel);
        tag.putInt("attackSpeedLevel", attackSpeedLevel);
        tag.putInt("rangedTimeLevel", rangedTimeLevel);
        tag.putInt("tieredzLevel", tieredzLevel);

        tag.putInt("maxHealthExp", maxHealthExp);
        tag.putInt("exhaustionExp", exhaustionExp);
        tag.putInt("miningSpeedExp", miningSpeedExp);
        tag.putInt("craftingTimeExp", craftingTimeExp);
        tag.putInt("speedExp", speedExp);
        tag.putInt("breathExp", breathExp);
        tag.putInt("damageExp", damageExp);
        tag.putInt("attackSpeedExp", attackSpeedExp);
        tag.putInt("rangedTimeExp", rangedTimeExp);
        tag.putInt("tieredzExp", tieredzExp);
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

    public int getExperiencePoints() { return experiencePoints; }
    public void setExperiencePoints(int value) {
        experiencePoints = value;
        checkOverallLevelUp();
    }

    public void addExperiencePoints(int value) {
        experiencePoints += value;
        checkOverallLevelUp();
    }

    public int getAvailableLevelPoints() { return availableLevelPoints; }
    public void setAvailableLevelPoints(int value) { availableLevelPoints = value; }

    public int getOverallLevel() { return overallLevel; }
    public void setOverallLevel(int value) { overallLevel = value; }

    public int getMaxHealthLevel() { return maxHealthLevel; }
    public void setMaxHealthLevel(int value) {
        maxHealthLevel = Math.min(value, MAX_STAT_LEVEL);
        updateStatModifier(ExerciseManager.StatType.MAX_HEALTH);
    }

    public int getExhaustionLevel() { return exhaustionLevel; }
    public void setExhaustionLevel(int value) {
        exhaustionLevel = Math.min(value, MAX_STAT_LEVEL);
        updateStatModifier(ExerciseManager.StatType.EXHAUSTION);
    }

    public int getMiningSpeedLevel() { return miningSpeedLevel; }
    public void setMiningSpeedLevel(int value) {
        miningSpeedLevel = Math.min(value, MAX_STAT_LEVEL);
        updateStatModifier(ExerciseManager.StatType.MINING_SPEED);
    }

    public int getCraftingTimeLevel() { return craftingTimeLevel; }
    public void setCraftingTimeLevel(int value) {
        craftingTimeLevel = Math.min(value, MAX_STAT_LEVEL);
        updateStatModifier(ExerciseManager.StatType.CRAFTING_TIME);
    }

    public int getSpeedLevel() { return speedLevel; }
    public void setSpeedLevel(int value) {
        speedLevel = Math.min(value, MAX_STAT_LEVEL);
        updateStatModifier(ExerciseManager.StatType.SPEED);
    }

    public int getBreathLevel() { return breathLevel; }
    public void setBreathLevel(int value) {
        breathLevel = Math.min(value, MAX_STAT_LEVEL);
        updateStatModifier(ExerciseManager.StatType.BREATH);
    }

    public int getDamageLevel() { return damageLevel; }
    public void setDamageLevel(int value) {
        damageLevel = Math.min(value, MAX_STAT_LEVEL);
        updateStatModifier(ExerciseManager.StatType.DAMAGE);
    }

    public int getAttackSpeedLevel() { return attackSpeedLevel; }
    public void setAttackSpeedLevel(int value) {
        attackSpeedLevel = Math.min(value, MAX_STAT_LEVEL);
        updateStatModifier(ExerciseManager.StatType.ATTACK_SPEED);
    }

    public int getRangedTimeLevel() { return rangedTimeLevel; }
    public void setRangedTimeLevel(int value) {
        rangedTimeLevel = Math.min(value, MAX_STAT_LEVEL);
        updateStatModifier(ExerciseManager.StatType.RANGED_TIME);
    }

    public int getTieredzLevel() { return tieredzLevel; }
    public void setTieredzLevel(int value) {
        tieredzLevel = Math.min(value, MAX_STAT_LEVEL);
        updateStatModifier(ExerciseManager.StatType.TIEREDZ);
    }

    public int getMaxHealthExp() { return maxHealthExp; }
    public void setMaxHealthExp(int value) {
        maxHealthExp = value;
        checkStatLevelUp(ExerciseManager.StatType.MAX_HEALTH);
    }

    public int getExhaustionExp() { return exhaustionExp; }
    public void setExhaustionExp(int value) {
        exhaustionExp = value;
        checkStatLevelUp(ExerciseManager.StatType.EXHAUSTION);
    }

    public int getMiningSpeedExp() { return miningSpeedExp; }
    public void setMiningSpeedExp(int value) {
        miningSpeedExp = value;
        checkStatLevelUp(ExerciseManager.StatType.MINING_SPEED);
    }

    public int getCraftingTimeExp() { return craftingTimeExp; }
    public void setCraftingTimeExp(int value) {
        craftingTimeExp = value;
        checkStatLevelUp(ExerciseManager.StatType.CRAFTING_TIME);
    }

    public int getSpeedExp() { return speedExp; }
    public void setSpeedExp(int value) {
        speedExp = value;
        checkStatLevelUp(ExerciseManager.StatType.SPEED);
    }

    public int getBreathExp() { return breathExp; }
    public void setBreathExp(int value) {
        breathExp = value;
        checkStatLevelUp(ExerciseManager.StatType.BREATH);
    }

    public int getDamageExp() { return damageExp; }
    public void setDamageExp(int value) {
        damageExp = value;
        checkStatLevelUp(ExerciseManager.StatType.DAMAGE);
    }

    public int getAttackSpeedExp() { return attackSpeedExp; }
    public void setAttackSpeedExp(int value) {
        attackSpeedExp = value;
        checkStatLevelUp(ExerciseManager.StatType.ATTACK_SPEED);
    }

    public int getRangedTimeExp() { return rangedTimeExp; }
    public void setRangedTimeExp(int value) {
        rangedTimeExp = value;
        checkStatLevelUp(ExerciseManager.StatType.RANGED_TIME);
    }

    public int getTieredzExp() { return tieredzExp; }
    public void setTieredzExp(int value) {
        tieredzExp = value;
        checkStatLevelUp(ExerciseManager.StatType.TIEREDZ);
    }


    public void addStatExperience(ExerciseManager.StatType stat, int amount) {
        switch (stat) {
            case MAX_HEALTH -> setMaxHealthExp(getMaxHealthExp() + amount);
            case EXHAUSTION -> setExhaustionExp(getExhaustionExp() + amount);
            case MINING_SPEED -> setMiningSpeedExp(getMiningSpeedExp() + amount);
            case CRAFTING_TIME -> setCraftingTimeExp(getCraftingTimeExp() + amount);
            case SPEED -> setSpeedExp(getSpeedExp() + amount);
            case BREATH -> setBreathExp(getBreathExp() + amount);
            case DAMAGE -> setDamageExp(getDamageExp() + amount);
            case ATTACK_SPEED -> setAttackSpeedExp(getAttackSpeedExp() + amount);
            case RANGED_TIME -> setRangedTimeExp(getRangedTimeExp() + amount);
            case TIEREDZ -> setTieredzExp(getTieredzExp() + amount);
        }
    }

    public int getStatExperience(ExerciseManager.StatType stat) {
        return switch (stat) {
            case MAX_HEALTH -> maxHealthExp;
            case EXHAUSTION -> exhaustionExp;
            case MINING_SPEED -> miningSpeedExp;
            case CRAFTING_TIME -> craftingTimeExp;
            case SPEED -> speedExp;
            case BREATH -> breathExp;
            case DAMAGE -> damageExp;
            case ATTACK_SPEED -> attackSpeedExp;
            case RANGED_TIME -> rangedTimeExp;
            case TIEREDZ -> tieredzExp;
        };
    }

    public int getStatLevel(ExerciseManager.StatType stat) {
        return switch (stat) {
            case MAX_HEALTH -> maxHealthLevel;
            case EXHAUSTION -> exhaustionLevel;
            case MINING_SPEED -> miningSpeedLevel;
            case CRAFTING_TIME -> craftingTimeLevel;
            case SPEED -> speedLevel;
            case BREATH -> breathLevel;
            case DAMAGE -> damageLevel;
            case ATTACK_SPEED -> attackSpeedLevel;
            case RANGED_TIME -> rangedTimeLevel;
            case TIEREDZ -> tieredzLevel;
        };
    }

    public int getExpRequiredForStatLevel(ExerciseManager.StatType stat) {
        int level = getStatLevel(stat);
        return BASE_EXP_REQUIREMENT + (level * 2);
    }

    public float getStatProgress(ExerciseManager.StatType stat) {
        int currentExp = getStatExperience(stat);
        int requiredExp = getExpRequiredForStatLevel(stat);
        return Math.min(1.0f, (float) currentExp / requiredExp);
    }

    public boolean isStatAtMaxLevel(ExerciseManager.StatType stat) {
        return getStatLevel(stat) >= MAX_STAT_LEVEL;
    }


    private void checkOverallLevelUp() {
        int oldLevel = overallLevel;
        int newLevel = 1 + (experiencePoints / 100); 

        if (newLevel > oldLevel) {
            int levelsGained = newLevel - oldLevel;
            availableLevelPoints += levelsGained;
            overallLevel = newLevel;
        }
    }

    private void checkStatLevelUp(ExerciseManager.StatType stat) {
        int currentLevel = getStatLevel(stat);
        if (currentLevel >= MAX_STAT_LEVEL) return;

        int currentExp = getStatExperience(stat);
        int requiredExp = getExpRequiredForStatLevel(stat);

        while (currentExp >= requiredExp && currentLevel < MAX_STAT_LEVEL) {
            currentExp -= requiredExp;
            currentLevel++;

            switch (stat) {
                case MAX_HEALTH -> setMaxHealthLevel(currentLevel);
                case EXHAUSTION -> setExhaustionLevel(currentLevel);
                case MINING_SPEED -> setMiningSpeedLevel(currentLevel);
                case CRAFTING_TIME -> setCraftingTimeLevel(currentLevel);
                case SPEED -> setSpeedLevel(currentLevel);
                case BREATH -> setBreathLevel(currentLevel);
                case DAMAGE -> setDamageLevel(currentLevel);
                case ATTACK_SPEED -> setAttackSpeedLevel(currentLevel);
                case RANGED_TIME -> setRangedTimeLevel(currentLevel);
                case TIEREDZ -> setTieredzLevel(currentLevel);
            }

            requiredExp = getExpRequiredForStatLevel(stat);
        }

        switch (stat) {
            case MAX_HEALTH -> maxHealthExp = currentExp;
            case EXHAUSTION -> exhaustionExp = currentExp;
            case MINING_SPEED -> miningSpeedExp = currentExp;
            case CRAFTING_TIME -> craftingTimeExp = currentExp;
            case SPEED -> speedExp = currentExp;
            case BREATH -> breathExp = currentExp;
            case DAMAGE -> damageExp = currentExp;
            case ATTACK_SPEED -> attackSpeedExp = currentExp;
            case RANGED_TIME -> rangedTimeExp = currentExp;
            case TIEREDZ -> tieredzExp = currentExp;
        }
    }

    private void updateStatModifier(ExerciseManager.StatType stat) {
        int level = getStatLevel(stat);

        float GLOBAL_MULTIPLIER = 2.0f;
        switch (stat) {
            case MAX_HEALTH -> maxHealth = (float) (1.0f + floor(level * 0.19f * GLOBAL_MULTIPLIER));
            case EXHAUSTION -> exhaustionModifier = Math.max(0.2f, 5.0f - (level * 0.04f * GLOBAL_MULTIPLIER));
            case MINING_SPEED -> miningSpeedModifier = 0.2f + (level * 0.008f * GLOBAL_MULTIPLIER);
            case CRAFTING_TIME -> craftingTimeModifier = Math.max(0.2f, 4.0f - (level * 0.03f * GLOBAL_MULTIPLIER));
            case SPEED -> speedModifier = 0.5f + (level * 0.005f * GLOBAL_MULTIPLIER);
            case BREATH -> breathModifier = Math.max(0.5f, 3.0f - (level * 0.02f * GLOBAL_MULTIPLIER));
            case DAMAGE -> damageModifier = 0.5f + (level * 0.005f * GLOBAL_MULTIPLIER);
            case ATTACK_SPEED -> attackSpeedModifier = 0.4f + (level * 0.006f * GLOBAL_MULTIPLIER);
            case RANGED_TIME -> rangedTimeModifier = 0.2f + (level * 0.008f * GLOBAL_MULTIPLIER);
            case TIEREDZ -> tieredzModifier = 0.2f + (level * 0.008f * GLOBAL_MULTIPLIER);
        }
    }

}