package com.FitBuddy.fitbuddy.UserPackage.UserFragments.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class UserSessionModel implements Serializable {
    public String date, excercise,limit,link,time,trainer,id;
    Date timeS;

    public UserSessionModel() {
    }

    public UserSessionModel(String date, String excercise, String limit, String link, String time, String trainer, String id, Date timeS) {
        this.date = date;
        this.excercise = excercise;
        this.limit = limit;
        this.link = link;
        this.time = time;
        this.trainer = trainer;
        this.id = id;
        this.timeS = timeS;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExcercise() {
        return excercise;
    }

    public void setExcercise(String excercise) {
        this.excercise = excercise;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimeS() {
        return timeS;
    }

    public void setTimeS(Date timeS) {
        this.timeS = timeS;
    }

    @Override
    public String toString() {
        return "UserSessionModel{" +
                "date='" + date + '\'' +
                ", excercise='" + excercise + '\'' +
                ", limit='" + limit + '\'' +
                ", link='" + link + '\'' +
                ", time='" + time + '\'' +
                ", trainer='" + trainer + '\'' +
                ", id='" + id + '\'' +
                ", timeS='" + timeS + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSessionModel that = (UserSessionModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
