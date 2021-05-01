package com.example.hair_care_tracker_api.controller;

import com.example.hair_care_tracker_api.entity.AppUser;
import com.example.hair_care_tracker_api.entity.Product;
import com.example.hair_care_tracker_api.entity.Routine;
import com.example.hair_care_tracker_api.repository.ProductRepository;
import com.example.hair_care_tracker_api.repository.RoutineRepository;
import com.example.hair_care_tracker_api.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Routine controller. Responsible for updating and returning user routines.
 */
@RestController
public class RoutineController {
    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * Constructor of the controller class. Used to inject repositories to work with the database.
     *
     * @param routineRepository handles routines
     * @param userRepository    handles users
     * @param productRepository handles products
     */
    public RoutineController(RoutineRepository routineRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.routineRepository = routineRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    /**
     * Method to return the list of routines of the user from the JWT token.
     *
     * @param httpServletRequest used to extract email of the user
     * @return JSON list of routines.
     */
    @GetMapping("/routines")
    List<Routine> getAll(HttpServletRequest httpServletRequest) {
        List<Routine> routines = routineRepository.findAllByUser((String) httpServletRequest.getAttribute("email"));
        for (Routine routine : routines) {
            routine.setUser(null);
            for (Product product : routine.getProducts()) {
                product.setUsers(null);
            }
        }
        return routines;
    }

    /**
     * Method to update the routine after the user saves the changes.
     *
     * @param routine            to be updated
     * @param httpServletRequest used to extract email of the user
     * @throws ServletException when the user cannot be found
     */
    @PutMapping("/routines")
    void saveRoutine(@RequestBody Routine routine, HttpServletRequest httpServletRequest) throws ServletException {
        Routine databaseRoutine = routineRepository.findByDateAndUser(routine.getDate(), (String) httpServletRequest.getAttribute("email"));
        AppUser appUser = userRepository.findByEmail((String) httpServletRequest.getAttribute("email"));

        List<Product> products = new ArrayList<>();

        for (Product product : routine.getProducts()) {
            products.add(productRepository.findByName(product.getName()));
        }

        if (databaseRoutine != null) {
            routineRepository.findById(databaseRoutine.getRoutineId())
                    .map(foundRoutine -> {
                        foundRoutine.setName(routine.getName());
                        foundRoutine.setDate(routine.getDate());
                        foundRoutine.setDescription(routine.getDescription());
                        foundRoutine.setEffects(routine.getEffects());
                        foundRoutine.setProducts(products);
                        foundRoutine.setUser(appUser);
                        return routineRepository.save(foundRoutine);
                    });
        } else {
            if (appUser != null) {
                routine.setUser(appUser);
                routine.setProducts(products);
                routineRepository.save(routine);
            } else {
                throw new ServletException();
            }
        }
    }
}
