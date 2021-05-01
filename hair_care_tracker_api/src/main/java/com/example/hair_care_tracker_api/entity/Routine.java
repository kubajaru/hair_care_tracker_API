package com.example.hair_care_tracker_api.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representation of our routine in the database.
 */
@Entity
public class Routine {
    // All methods are necessary even if they are not explicitly used.
    private @Id
    @GeneratedValue
    Long routineId;
    private String name;
    private String description;
    private String effects;
    private String date;

    @ManyToMany
    List<Product> products = new ArrayList<>();

    @OneToOne
    AppUser appUser;

    public Routine() {
    }

    public Routine(String name, String description, String effects, String date) {
        this.name = name;
        this.description = description;
        this.effects = effects;
        this.date = date;
    }

    public Long getRoutineId() {
        return routineId;
    }

    public void setRoutineId(Long routineId) {
        this.routineId = routineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public AppUser getUser() {
        return appUser;
    }

    public void setUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public String toString() {
        return "Routine{" +
                "routineId=" + routineId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", effects='" + effects + '\'' +
                ", date='" + date + '\'' +
                ", products=" + products +
                ", appUser=" + appUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Routine routine = (Routine) o;
        return routineId.equals(routine.routineId) && name.equals(routine.name) && description.equals(routine.description) && Objects.equals(effects, routine.effects) && date.equals(routine.date) && products.equals(routine.products) && appUser.equals(routine.appUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routineId, name, description, effects, date, products, appUser);
    }
}
