package com.example.hair_care_tracker_api.controller;

import com.example.hair_care_tracker_api.entity.AppUser;
import com.example.hair_care_tracker_api.entity.Profile;
import com.example.hair_care_tracker_api.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

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
    void getUserProfileTest() throws Exception {
        AppUser appUser = new AppUser("nickname", "email", "password");
        Profile profile = new Profile("12", "56", "Natural", "High", "2c", "Medium", "Medium", "", appUser);

        when(profileRepository.findByEmail("email")).thenReturn(profile);

        mockMvc.perform(get("/profile").header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbCIsInJvbGUiOiJ1c2VyIn0.h76xc7N0Ud7FhjJkqh3-RDzO_mqu1Bt8QpRM4RMrPV4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age", is("12")))
                .andExpect(jsonPath("$.length", is("56")))
                .andExpect(jsonPath("$.porosity", is("High")))
                .andExpect(jsonPath("$.state", is("Natural")))
                .andExpect(jsonPath("$.curlType", is("2c")))
                .andExpect(jsonPath("$.thickness", is("Medium")))
                .andExpect(jsonPath("$.denseness", is("Medium")))
                .andExpect(jsonPath("$.problems", is("")))
                .andExpect(jsonPath("$.user", nullValue()));
    }
}