package com.example.hair_care_tracker_api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representation of our product in the database.
 */
@Entity
public class Product {
    // All methods are necessary even if they are not explicitly used.
    private @Id
    @GeneratedValue
    Long productId;
    private String name; // MUST BE UNIQUE!!!!
    private String brand;
    private String description;
    private int capacity;

    @ManyToMany
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToMany
    private List<AppUser> appUsers = new ArrayList<>();


    public Product() {
    }

    public Product(String name, String brand, String description, int capacity) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.capacity = capacity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<AppUser> getUsers() {
        return appUsers;
    }

    public void setUsers(List<AppUser> appUsers) {
        this.appUsers = appUsers;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", ingredients=" + ingredients +
                ", appUsers=" + appUsers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return capacity == product.capacity && name.equals(product.name) && brand.equals(product.brand) && description.equals(product.description) && ingredients.equals(product.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brand, description, capacity, ingredients);
    }
}
