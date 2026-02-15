package com.neuromuser.shittofit.exercise;

public enum ExerciseType {
    PULLUPS("exercise.stf.pullups"),
    PUSHUPS("exercise.stf.pushups"),
    BURPIES("exercise.stf.burpies"),
    SQUATS("exercise.stf.squats"),
    PRESS("exercise.stf.press"),
    DUMBBELLS("exercise.stf.dumbbells"),
    PLANK("exercise.stf.plank"),
    RUN_WALK("exercise.stf.run_walk");

    private final String translationKey;

    ExerciseType(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getTranslationKey() {
        return translationKey;
    }
}