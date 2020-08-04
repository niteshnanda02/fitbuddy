package com.FitBuddy.fitbuddy.UserPackage.PlanPackage;

import java.io.Serializable;

public class PlanModel implements Serializable {
    private String packName,price,workout,no_of_sessions;
    private Integer image;

    public PlanModel() {
    }

    public PlanModel(String packName, String price, String workout, String no_of_sessions,Integer image) {
        this.packName = packName;
        this.price = price;
        this.workout = workout;
        this.no_of_sessions = no_of_sessions;
        this.image=image;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWorkout() {
        return workout;
    }

    public void setWorkout(String workout) {
        this.workout = workout;
    }

    public String getNo_of_sessions() {
        return no_of_sessions;
    }

    public void setNo_of_sessions(String no_of_sessions) {
        this.no_of_sessions = no_of_sessions;
    }
}
