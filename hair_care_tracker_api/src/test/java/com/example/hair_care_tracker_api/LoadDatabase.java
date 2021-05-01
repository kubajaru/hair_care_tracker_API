package com.example.hair_care_tracker_api;

import com.example.hair_care_tracker_api.entity.AppUser;
import com.example.hair_care_tracker_api.entity.Ingredient;
import com.example.hair_care_tracker_api.entity.Product;
import com.example.hair_care_tracker_api.entity.Routine;
import com.example.hair_care_tracker_api.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("test")
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");

        return dataSource;
    }

    /**
     * Method to load the database with exemplary data for testing purposes.
     *
     * @param userRepository       handles users
     * @param profileRepository    handles profiles
     * @param productRepository    handle products
     * @param ingredientRepository handles ingredients
     * @return command line logs
     */
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, ProfileRepository profileRepository, ProductRepository productRepository, IngredientRepository ingredientRepository, RoutineRepository routineRepository) {
        return args -> {
            log.info("Preloading " + userRepository.save(new AppUser("nickname", "email", "$2b$12$SDL2oZdnyEKCOPbCyoC2vuBLF1w/2WbxA5JWn9lbx3xhCEUJTRWqG")));
            com.example.hair_care_tracker_api.entity.Profile profile = new com.example.hair_care_tracker_api.entity.Profile("12", "56", "Natural", "High", "2c", "Medium", "Medium", "", userRepository.findByEmail("email"));
            log.info("Preloading " + profileRepository.save(profile));
            Ingredient ingredient = new Ingredient("Aqua", "Humectant");
            ingredientRepository.save(ingredient);
            Ingredient ingredient1 = new Ingredient("Cetearyl Alcohol", "Emolient");
            ingredientRepository.save(ingredient1);
            Product product = new Product("Liquid Gold", "HairyTaleCosmetics", "description", 250);
            product.getIngredients().add(ingredient);
            product.getIngredients().add(ingredient1);
            product.getUsers().add(userRepository.findByEmail("email"));
            log.info("Preloading " + productRepository.save(product));
            Product product1 = new Product("Dragon Wash", "HairyTaleCosmetics", "description", 250);
            product1.getIngredients().add(ingredient);
            product1.getIngredients().add(ingredient1);
            log.info("Preloading " + productRepository.save(product1));
            Routine routine = new Routine("Rypacz", "desc", "effects", "2021-04-08T20:21:49.698195");
            routine.setUser(userRepository.findByEmail("email"));
            routine.getProducts().add(product);
            Routine routine1 = new Routine("Soft", "desc", "effects", "2021-04-12T20:21:49.698195");
            routine1.setUser(userRepository.findByEmail("email"));
            log.info("Preloading " + routineRepository.save(routine));
            log.info("Preloading " + routineRepository.save(routine1));
        };
    }
}
