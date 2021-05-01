package com.example.hair_care_tracker_api.repository;

import com.example.hair_care_tracker_api.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
