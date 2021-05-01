package com.example.hair_care_tracker_api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

/**
 * Representation of our profile in the database.
 */
@Entity
public class Profile {
    // All methods are necessary even if they are not explicitly used.
    private @Id
    @GeneratedValue
    Long profileId;
    private String age;
    private String length;
    private String state;
    private String porosity;
    private String curlType;
    private String thickness;
    private String denseness;
    private String problems;

    @OneToOne
    private AppUser appUser;

    public Profile() {
    }

    public Profile(String age, String length, String state, String porosity, String curlType, String thickness, String denseness, String problems, AppUser appUser) {
        this.age = age;
        this.length = length;
        this.state = state;
        this.porosity = porosity;
        this.curlType = curlType;
        this.thickness = thickness;
        this.denseness = denseness;
        this.problems = problems;
        this.appUser = appUser;
    }


    public Long getProfileId() {
        return profileId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPorosity() {
        return porosity;
    }

    public void setPorosity(String porosity) {
        this.porosity = porosity;
    }

    public String getCurlType() {
        return curlType;
    }

    public void setCurlType(String curlType) {
        this.curlType = curlType;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getDenseness() {
        return denseness;
    }

    public void setDenseness(String denseness) {
        this.denseness = denseness;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public AppUser getUser() {
        return appUser;
    }

    public void setUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return profileId.equals(profile.profileId) && age.equals(profile.age) && length.equals(profile.length) && state.equals(profile.state) && porosity.equals(profile.porosity) && curlType.equals(profile.curlType) && thickness.equals(profile.thickness) && denseness.equals(profile.denseness) && Objects.equals(problems, profile.problems) && appUser.equals(profile.appUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, age, length, state, porosity, curlType, thickness, denseness, problems, appUser);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileId=" + profileId +
                ", age='" + age + '\'' +
                ", length='" + length + '\'' +
                ", state='" + state + '\'' +
                ", porosity='" + porosity + '\'' +
                ", curlType='" + curlType + '\'' +
                ", thickness='" + thickness + '\'' +
                ", denseness='" + denseness + '\'' +
                ", problems='" + problems + '\'' +
                ", appUser=" + appUser +
                '}';
    }
}
