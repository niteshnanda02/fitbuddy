package com.FitBuddy.fitbuddy.UserPackage.UserFragments.model;

import java.util.ArrayList;
import java.util.List;

public class TrainerAssignExercise {

    public java.lang.String exercise_name;
    public List<SessionsDetails> sessionsDetailsList;

    public TrainerAssignExercise() {
        sessionsDetailsList=new ArrayList<>();
    }

    public TrainerAssignExercise(java.lang.String exercise_name, List<SessionsDetails> sessionsDetailsList) {
        this.exercise_name = exercise_name;
        this.sessionsDetailsList = sessionsDetailsList;
    }

    public java.lang.String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(java.lang.String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public List<SessionsDetails> getSessionsDetailsList() {
        return sessionsDetailsList;
    }

    public void setSessionsDetailsList(List<SessionsDetails> sessionsDetailsList) {
        this.sessionsDetailsList = sessionsDetailsList;
    }
}
