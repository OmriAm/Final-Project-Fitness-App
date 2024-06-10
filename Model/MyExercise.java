package com.example.fitnessapp.Model;

public class MyExercise {

    String ExerciseName,ExerciseDescription,ExerciseDuration,ExerciseId;
    private long ExerciseTime;

    public MyExercise(String exerciseName, String exerciseDescription, String exerciseDuration, long exerciseTime, String exerciseId) {
        ExerciseName = exerciseName;
        ExerciseDescription = exerciseDescription;
        ExerciseDuration = exerciseDuration;
        ExerciseTime = exerciseTime;
        ExerciseId = exerciseId;
    }

    public String getExerciseName() {
        return ExerciseName;
    }

    public String getExerciseDescription() {
        return ExerciseDescription;
    }

    public String getExerciseDuration() {
        return ExerciseDuration;
    }

    public long getExerciseTime() {
        return ExerciseTime;
    }

    public String getExerciseId() {
        return ExerciseId;
    }
}
