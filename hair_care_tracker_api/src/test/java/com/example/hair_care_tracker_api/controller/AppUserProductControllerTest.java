package com.example.hair_care_tracker_api.controller;

import com.example.hair_care_tracker_api.entity.AppUser;
import com.example.hair_care_tracker_api.entity.Product;
import com.example.hair_care_tracker_api.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserProductController.class)
class AppUserProductControllerTest {
    @MockBean
    UserRepository userRepository;
    @MockBean
    ProductRepository productRepository;
    @MockBean
    ProfileRepository profileRepository;
    @MockBean
    RoutineRepository routineRepository;
    @MockBean
    IngredientRepository ingredientRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUserProducts() throws Exception {
        Product product = new Product("Liquid Gold", "HairyTaleCosmetics", "description", 250);
        Product product1 = new Product("Dragon Wash", "HairyTaleCosmetics", "description", 250);
        AppUser appUser = new AppUser("nickname", "email", "password");
        product1.setUsers(Collections.singletonList(appUser));
        List<Product> allProducts = Arrays.asList(product1, product);

        when(productRepository.findAll()).thenReturn(allProducts);

        when(userRepository.findByEmail("email")).thenReturn(appUser);

        mockMvc.perform(get("/userproducts").header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbCIsInJvbGUiOiJ1c2VyIn0.h76xc7N0Ud7FhjJkqh3-RDzO_mqu1Bt8QpRM4RMrPV4"))
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Dragon Wash")))
                .andExpect(jsonPath("$[0].users", nullValue()));
    }

    @Test
    void updateProducts() {
        // TODO: Test put
    }
}