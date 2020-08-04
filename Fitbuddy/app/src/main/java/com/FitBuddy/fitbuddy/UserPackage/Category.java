package com.FitBuddy.fitbuddy.UserPackage;

import java.io.Serializable;

public class Category implements Serializable {
    public String focus_area,workout,workout_time;

    public Category() {
    }

    public Category(String focus_area, String workout, String workout_time) {
        this.focus_area = focus_area;
        this.workout = workout;
        this.workout_time = workout_time;
    }
}
