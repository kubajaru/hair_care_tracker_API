package com.example.hair_care_tracker_api.controller;

import com.example.hair_care_tracker_api.entity.AppUser;
import com.example.hair_care_tracker_api.entity.Product;
import com.example.hair_care_tracker_api.repository.ProductRepository;
import com.example.hair_care_tracker_api.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Product controller. Responsible for returning all products.
 */
@RestController
public class ProductController {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * Constructor of controller class. Used to inject the repositories.
     *
     * @param productRepository handles products;
     */
    public ProductController(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    /**
     * Method to return all the products available in the database not belonging to the user from JWT token.
     *
     * @return JSON list of all products
     */
    @GetMapping("/products")
    List<Product> getAll(HttpServletRequest httpServletRequest) {
        List<Product> allProducts = productRepository.findAll();
        AppUser appUser = userRepository.findByEmail((String) httpServletRequest.getAttribute("email"));
        List<Product> userProducts = new ArrayList<>();
        for (Product product : allProducts) {
            if (!product.getUsers().contains(appUser)) {
                product.setUsers(null);
                userProducts.add(product);
            }
        }
        return userProducts;
    }
}
