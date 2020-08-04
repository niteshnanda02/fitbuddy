package com.FitBuddy.fitbuddy.model;

public class Upload {
    private String name;
    private String videoulr;

    public Upload() {
    }

    public Upload(String name, String videoulr) {
        if (name.trim().equals(""))
            name="NO name";
        this.name = name;
        this.videoulr = videoulr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoulr() {
        return videoulr;
    }

    public void setVideoulr(String videoulr) {
        this.videoulr = videoulr;
    }
}
