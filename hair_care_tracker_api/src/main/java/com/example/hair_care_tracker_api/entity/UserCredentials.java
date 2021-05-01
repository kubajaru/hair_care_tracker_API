package com.example.hair_care_tracker_api.entity;

/**
 * Class used only in the log in stage for passing user credentials withour showing the whole user class structure.
 */
public class UserCredentials {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
