package com.example.hair_care_tracker_api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Representation of our user in the database.
 */
@Entity
public class AppUser {
    // All methods are necessary even if they are not explicitly used.
    private @Id
    @GeneratedValue
    Long userId;
    private String nickname;
    private String email;
    private String password;

    public AppUser() {
    }

    public AppUser(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

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

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return userId.equals(appUser.userId) && nickname.equals(appUser.nickname) && email.equals(appUser.email) && password.equals(appUser.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, nickname, email, password);
    }
}
