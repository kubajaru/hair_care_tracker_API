package com.example.hair_care_tracker_api.controller;

import com.example.hair_care_tracker_api.entity.AppUser;
import com.example.hair_care_tracker_api.entity.Product;
import com.example.hair_care_tracker_api.repository.ProductRepository;
import com.example.hair_care_tracker_api.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * User Product controller. Responsible for returning products of a user.
 */
@RestController
public class UserProductController {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * Constructor of controller class. Used to inject  repositories to work with the database.
     *
     * @param userRepository    handles users
     * @param productRepository handles products
     */
    public UserProductController(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    /**
     * Method to return all products of the user from the sent token.
     *
     * @param httpServletRequest used to retrieve user email
     * @return JSON list of all products owned by the user
     */
    @GetMapping("/userproducts")
    List<Product> getUserProducts(HttpServletRequest httpServletRequest) {
        AppUser appUser = userRepository.findByEmail((String) httpServletRequest.getAttribute("email"));
        List<Product> products = findUserProducts(appUser);
        for (Product product : products) {
            product.setUsers(null);
        }
        return products;
    }

    /**
     * Private method to remove products that do not belong to the user from the list of all products.
     *
     * @param appUser represents the  user for who we look for products
     * @return List of all products that belong to the passed user
     */
    private List<Product> findUserProducts(AppUser appUser) {
        List<Product> products = productRepository.findAll();
        List<Product> userProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getUsers().contains(appUser)) {
                userProducts.add(product);
            }
        }
        return userProducts;
    }

    /**
     * Method to save all the changes made by the user to the list of  its products.
     *
     * @param productsAfterUpdate JSON list of products from the client after changes
     * @param httpServletRequest  needed to extract the email of the user
     */
    @PutMapping("/userproducts")
    void updateProducts(@RequestBody List<Product> productsAfterUpdate, HttpServletRequest httpServletRequest) {
        AppUser appUser = userRepository.findByEmail((String) httpServletRequest.getAttribute("email"));
        List<Product> userProductsBeforeUpdate = findUserProducts(appUser);
        // Should remove products not present after the update from the database
        for (Product productBeforeUpdate : userProductsBeforeUpdate) {
            boolean contains = false;
            for (Product productAfterUpdate : productsAfterUpdate) {
                if (productBeforeUpdate.getName().equals(productAfterUpdate.getName())) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                removeProduct(productBeforeUpdate, appUser);
            }
        }
        // Should add not existing products to the database
        for (Product product : productsAfterUpdate) {
            if (!userProductsBeforeUpdate.contains(product)) {
                addProduct(product, appUser);
            }
        }
    }

    /**
     * Private method to add product to the user.
     *
     * @param product new product to be added
     * @param appUser user to who we are adding the product
     */
    private void addProduct(Product product, AppUser appUser) {
        Product databaseProduct = productRepository.findByName(product.getName());
        if (!databaseProduct.getUsers().contains(appUser)) {
            databaseProduct.getUsers().add(appUser);
            productRepository.save(databaseProduct);
        }
    }

    /**
     * Private method to remove product to the user.
     *
     * @param product new product to be removed
     * @param appUser    user to who we are removing the product
     */
    private void removeProduct(Product product, AppUser appUser) {
        Product databaseProduct = productRepository.findByName(product.getName());
        databaseProduct.getUsers().remove(appUser);
        productRepository.save(databaseProduct);
    }


}
