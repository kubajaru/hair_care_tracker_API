package com.example.hair_care_tracker_api.controller;

import com.example.hair_care_tracker_api.entity.AppUser;
import com.example.hair_care_tracker_api.entity.Product;
import com.example.hair_care_tracker_api.entity.Routine;
import com.example.hair_care_tracker_api.repository.ProductRepository;
import com.example.hair_care_tracker_api.repository.RoutineRepository;
import com.example.hair_care_tracker_api.repository.UserRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RoutineRepository routineRepository;

    public UserController(UserRepository userRepository, ProductRepository productRepository, RoutineRepository routineRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.routineRepository = routineRepository;
    }

    @DeleteMapping("/users/{email}")
    void deleteUser(@PathVariable String email) {
        AppUser appUser = userRepository.findByEmail(email);
        List<Product> userProducts = productRepository.findAll();
        for (Product p : userProducts) {
            p.getUsers().remove(appUser);
        }

        List<Routine> userRoutines = routineRepository.findAllByUser(appUser.getEmail());
        routineRepository.deleteAll(userRoutines);

        userRepository.delete(appUser);
    }
}
