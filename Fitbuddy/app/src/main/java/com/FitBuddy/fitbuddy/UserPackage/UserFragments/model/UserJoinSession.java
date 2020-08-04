package com.FitBuddy.fitbuddy.UserPackage.UserFragments.model;

import java.io.Serializable;
import java.util.Objects;

public class UserJoinSession implements Serializable {

    private String name;
    private String mobile;

    public UserJoinSession() {
    }

    public UserJoinSession(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserJoinSession{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserJoinSession that = (UserJoinSession) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(mobile, that.mobile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mobile);
    }
}
