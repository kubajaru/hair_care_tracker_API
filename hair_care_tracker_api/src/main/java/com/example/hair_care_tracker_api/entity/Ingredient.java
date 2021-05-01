package com.example.hair_care_tracker_api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Representation of our ingredient in the database.
 */
@Entity
public class Ingredient {
    // All methods are necessary even if they are not explicitly used.
    private @Id
    @GeneratedValue
    Long ingredientId;
    private String name;
    private String category;

    public Ingredient() {
    }

    public Ingredient(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return name.equals(that.name) && category.equals(that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category);
    }
}
