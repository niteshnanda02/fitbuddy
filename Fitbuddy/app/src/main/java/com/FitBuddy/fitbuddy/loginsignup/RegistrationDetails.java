package com.FitBuddy.fitbuddy.loginsignup;

import com.FitBuddy.fitbuddy.UserPackage.Category;

import java.io.Serializable;

public class RegistrationDetails implements Serializable {
    public String gender;
    public String age;
    public String height;
    public String weight;
    public String profession;
    public String proficiency_level;
    public Category category;
    public RegistrationDetails() {
    }

    public RegistrationDetails(String gender, String age, String height, String weight, String profession, String proficiency_level, Category category) {
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.profession = profession;
        this.proficiency_level = proficiency_level;
        this.category = category;
    }
}
