package com.FitBuddy.fitbuddy.UserPackage.UserFragments.model;
import java.io.Serializable;
import java.lang.String;
public class AvailableExerciseDetails implements Serializable {
    String level,name,category,date,time;

    public AvailableExerciseDetails(String level, String name, String category, String date, String time) {
        this.level = level;
        this.name = name;
        this.category = category;
        this.date = date;
        this.time = time;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
