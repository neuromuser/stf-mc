package com.neuromuser.shittofit.exercise;

public enum ExerciseType {
    PUSHUPS("exercise.stf.pushups", 15),
    BURPIES("exercise.stf.burpies", 20),
    SQUATS("exercise.stf.squats", 15),
    PRESS("exercise.stf.press", 10),
    DUMBBELLS("exercise.stf.dumbbells", 15),
    RUN_WALK("exercise.stf.run_walk", 25);

    private final String translationKey;
    private final int xpReward;

    ExerciseType(String translationKey, int xpReward) {
        this.translationKey = translationKey;
        this.xpReward = xpReward;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public int getXpReward() {
        return xpReward;
    }
}