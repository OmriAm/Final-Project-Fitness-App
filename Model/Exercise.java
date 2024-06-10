package com.example.fitnessapp.Model;

public class Exercise {
    String exerciseName,bodyParts,equipments,level,exerciseId,link;
    int exerciseImg;

    public Exercise(String exerciseName, String bodyParts, String equipments, String level, String exerciseId
   , int exerciseImg,String link) {
        this.exerciseName = exerciseName;
        this.bodyParts = bodyParts;
        this.equipments = equipments;
        this.level = level;
        this.exerciseId = exerciseId;
        this.exerciseImg=exerciseImg;
        this.link=link;
    }

    public String getLink() {
        return link;
    }

    public int getExerciseImg() {
        return exerciseImg;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getBodyParts() {
        return bodyParts;
    }

    public String getEquipments() {
        return equipments;
    }

    public String getLevel() {
        return level;
    }

    public String getExerciseId() {
        return exerciseId;
    }
}
