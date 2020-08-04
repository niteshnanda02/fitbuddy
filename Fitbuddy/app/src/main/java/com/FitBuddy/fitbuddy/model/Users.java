package com.FitBuddy.fitbuddy.model;

import com.FitBuddy.fitbuddy.UserPackage.UserFragments.model.UserSessionModel;
import com.FitBuddy.fitbuddy.loginsignup.RegistrationDetails;

import java.io.Serializable;
import java.util.List;

public class Users implements Serializable {
    public String email,mobile,token,name;
    public RegistrationDetails registrationDetails=new RegistrationDetails();
    public List<UserSessionModel> sessionsDetailsList;
    public List<UserSessionModel> sessionCompletedList;
    public Users() {
    }

    public Users(String email, String mobile, String token, String name, RegistrationDetails registrationDetails, List<UserSessionModel> sessionsDetailsList, List<UserSessionModel> sessionCompletedList) {
        this.email = email;
        this.mobile = mobile;
        this.token = token;
        this.name = name;
        this.registrationDetails = registrationDetails;
        this.sessionsDetailsList = sessionsDetailsList;
        this.sessionCompletedList = sessionCompletedList;
    }

    public List<UserSessionModel> getSessionCompletedList() {
        return sessionCompletedList;
    }

    public void setSessionCompletedList(List<UserSessionModel> sessionCompletedList) {
        this.sessionCompletedList = sessionCompletedList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegistrationDetails getRegistrationDetails() {
        return registrationDetails;
    }

    public void setRegistrationDetails(RegistrationDetails registrationDetails) {
        this.registrationDetails = registrationDetails;
    }

    public List<UserSessionModel> getSessionsDetailsList() {
        return sessionsDetailsList;
    }

    public void setSessionsDetailsList(List<UserSessionModel> sessionsDetailsList) {
        this.sessionsDetailsList = sessionsDetailsList;
    }
}

