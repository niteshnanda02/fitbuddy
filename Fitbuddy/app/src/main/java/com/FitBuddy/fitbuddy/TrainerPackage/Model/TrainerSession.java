package com.FitBuddy.fitbuddy.TrainerPackage.Model;

import java.util.Objects;

public class TrainerSession {
    String date,excercise,id,limit,link,time,trainer;

    public TrainerSession() {
    }

    public TrainerSession(String date, String excercise, String id, String limit, String link, String time, String trainer) {
        this.date = date;
        this.excercise = excercise;
        this.id = id;
        this.limit = limit;
        this.link = link;
        this.time = time;
        this.trainer = trainer;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "TrainerSession{" +
                "date='" + date + '\'' +
                ", excercise='" + excercise + '\'' +
                ", id='" + id + '\'' +
                ", limit='" + limit + '\'' +
                ", link='" + link + '\'' +
                ", time='" + time + '\'' +
                ", trainer='" + trainer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainerSession that = (TrainerSession) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(id, that.id) &&
                Objects.equals(time, that.time) &&
                Objects.equals(trainer, that.trainer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, id, time, trainer);
    }
}
