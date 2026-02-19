package com.neuromuser.stf.config;

import com.neuromuser.stf.exercise.ExerciseManager;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.EnumMap;
import java.util.Map;

@Config(name = "stf_client")
public class ClientConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public ExerciseConfig exercise1 = new ExerciseConfig("exercise.stf.pullups", true, 0, 0, 15, 0, 0, 0, 20, 0, 0, 10, 0);

    @ConfigEntry.Gui.CollapsibleObject
    public ExerciseConfig exercise2 = new ExerciseConfig("exercise.stf.pushups", true, 0, 0, 0, 0, 0, 0, 15, 10, 0, 10, 0);

    @ConfigEntry.Gui.CollapsibleObject
    public ExerciseConfig exercise3 = new ExerciseConfig("exercise.stf.burpies", true, 0, 15, 20, 0, 0, 0, 0, 0, 20, 0, 0);

    @ConfigEntry.Gui.CollapsibleObject
    public ExerciseConfig exercise4 = new ExerciseConfig("exercise.stf.squats", true, 15, 0, 0, 20, 10, 0, 0, 0, 0, 0, 0);

    @ConfigEntry.Gui.CollapsibleObject
    public ExerciseConfig exercise5 = new ExerciseConfig("exercise.stf.press", true, 5, 15, 0, 0, 0, 0, 15, 0, 0, 0, 0);

    @ConfigEntry.Gui.CollapsibleObject
    public ExerciseConfig exercise6 = new ExerciseConfig("exercise.stf.dumbbells", true, 0, 0, 15, 10, 0, 0, 0, 15, 0, 0, 0);

    @ConfigEntry.Gui.CollapsibleObject
    public ExerciseConfig exercise7 = new ExerciseConfig("exercise.stf.plank", true, 0, 40, 0, 0, 0, 40, 0, 0, 0, 0, 0);

    @ConfigEntry.Gui.CollapsibleObject
    public ExerciseConfig exercise8 = new ExerciseConfig("exercise.stf.run_walk", true, 0, 0, 0, 0, 100, 60, 0, 0, 0, 40, 90);

    @ConfigEntry.Gui.CollapsibleObject
    public ExerciseConfig exercise9 = new ExerciseConfig("Custom Exercise 1", false, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

    @ConfigEntry.Gui.CollapsibleObject
    public ExerciseConfig exercise10 = new ExerciseConfig("Custom Exercise 2", false, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

    @ConfigEntry.Gui.Excluded
    public static final int MAX_EXERCISES = 10;

    public ExerciseConfig getExercise(int index) {
        return switch (index) {
            case 0 -> exercise1;
            case 1 -> exercise2;
            case 2 -> exercise3;
            case 3 -> exercise4;
            case 4 -> exercise5;
            case 5 -> exercise6;
            case 6 -> exercise7;
            case 7 -> exercise8;
            case 8 -> exercise9;
            case 9 -> exercise10;
            default -> null;
        };
    }

    public static class ExerciseConfig implements ConfigData {

        public String name = "Exercise";
        public boolean enabled = true;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
        public int maxHealthExp = 0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
        public int exhaustionExp = 0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
        public int miningSpeedExp = 0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
        public int craftingTimeExp = 0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
        public int speedExp = 0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
        public int breathExp = 0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
        public int damageExp = 0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
        public int attackSpeedExp = 0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
        public int rangedTimeExp = 0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
        public int tieredzExp = 0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
        public int bonusBaseExp = 0;

        public ExerciseConfig() {}

        public ExerciseConfig(String name, boolean enabled,
                              int maxHealth, int exhaustion, int miningSpeed,
                              int craftingTime, int speed, int breath,
                              int damage, int attackSpeed, int rangedTime,
                              int tieredz, int bonusBase) {
            this.name = name;
            this.enabled = enabled;
            this.maxHealthExp = maxHealth;
            this.exhaustionExp = exhaustion;
            this.miningSpeedExp = miningSpeed;
            this.craftingTimeExp = craftingTime;
            this.speedExp = speed;
            this.breathExp = breath;
            this.damageExp = damage;
            this.attackSpeedExp = attackSpeed;
            this.rangedTimeExp = rangedTime;
            this.tieredzExp = tieredz;
            this.bonusBaseExp = bonusBase;
        }

        public Map<ExerciseManager.StatType, Integer> getExpMap() {
            Map<ExerciseManager.StatType, Integer> map = new EnumMap<>(ExerciseManager.StatType.class);
            if (maxHealthExp > 0) map.put(ExerciseManager.StatType.MAX_HEALTH, maxHealthExp);
            if (exhaustionExp > 0) map.put(ExerciseManager.StatType.EXHAUSTION, exhaustionExp);
            if (miningSpeedExp > 0) map.put(ExerciseManager.StatType.MINING_SPEED, miningSpeedExp);
            if (craftingTimeExp > 0) map.put(ExerciseManager.StatType.CRAFTING_TIME, craftingTimeExp);
            if (speedExp > 0) map.put(ExerciseManager.StatType.SPEED, speedExp);
            if (breathExp > 0) map.put(ExerciseManager.StatType.BREATH, breathExp);
            if (damageExp > 0) map.put(ExerciseManager.StatType.DAMAGE, damageExp);
            if (attackSpeedExp > 0) map.put(ExerciseManager.StatType.ATTACK_SPEED, attackSpeedExp);
            if (rangedTimeExp > 0) map.put(ExerciseManager.StatType.RANGED_TIME, rangedTimeExp);
            if (tieredzExp > 0) map.put(ExerciseManager.StatType.TIEREDZ, tieredzExp);
            return map;
        }

        public int getExpForStat(ExerciseManager.StatType stat) {
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
    }
}