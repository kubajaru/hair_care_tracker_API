package com.example.hair_care_tracker_api.filter;

import com.example.hair_care_tracker_api.controller.ProfileController;
import com.example.hair_care_tracker_api.entity.AppUser;
import com.example.hair_care_tracker_api.entity.Profile;
import com.example.hair_care_tracker_api.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.ServletException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
class AppUserFilterTest {
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
    void doFilterTestEmptyHeader() throws Exception {
        AppUser appUser = new AppUser("nickname", "email", "password");
        Profile profile = new Profile("12", "56", "Natural", "High", "2c", "Medium", "Medium", "", appUser);

        when(profileRepository.findByEmail("email")).thenReturn(profile);

        try {
            mockMvc.perform(get("/profile").header("authorization", ""))
                    .andExpect(status().is5xxServerError())
                    .andExpect(status().reason("Wrong or empty header"));
        } catch (ServletException ex) {
            Assertions.assertEquals(ex.getMessage(), "Wrong or empty header");
        }
    }

    @Test
    void doFilterTestWrongHeader() throws Exception {
        AppUser appUser = new AppUser("nickname", "email", "password");
        Profile profile = new Profile("12", "56", "Natural", "High", "2c", "Medium", "Medium", "", appUser);

        when(profileRepository.findByEmail("email")).thenReturn(profile);

        try {
            mockMvc.perform(get("/profile").header("authorization", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbCIsInJvbGUiOiJ1c2VyIn0.h76xc7N0Ud7FhjJkqh3-RDzO_mqu1Bt8QpRM4RMrPV4"))
                    .andExpect(status().is5xxServerError())
                    .andExpect(status().reason("Wrong or empty header"));
        } catch (ServletException ex) {
            Assertions.assertEquals(ex.getMessage(), "Wrong or empty header");
        }
    }

    @Test
    void doFilterTestWrongKey() throws Exception {
        AppUser appUser = new AppUser("nickname", "email", "password");
        Profile profile = new Profile("12", "56", "Natural", "High", "2c", "Medium", "Medium", "", appUser);

        when(profileRepository.findByEmail("email")).thenReturn(profile);

        try {
            mockMvc.perform(get("/profile").header("authorization", "Bearer yJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbCIsInJvbGUiOiJ1c2VyIn0.h76xc7N0Ud7FhjJkqh3-RDzO_mqu1Bt8QpRM4RMrPV4"))
                    .andExpect(status().is5xxServerError())
                    .andExpect(status().reason("Wrong key"));
        } catch (ServletException ex) {
            Assertions.assertEquals(ex.getMessage(), "Wrong key");
        }
    }

    @Test
    void doFilterTestCorrectToken() throws Exception {
        AppUser appUser = new AppUser("nickname", "email", "password");
        Profile profile = new Profile("12", "56", "Natural", "High", "2c", "Medium", "Medium", "", appUser);

        when(profileRepository.findByEmail("email")).thenReturn(profile);
        mockMvc.perform(get("/profile").header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGV4YW1wbGUuY29tIiwicm9sZSI6InVzZXIifQ.k67Wl0y39g76qxTFmL_Y8jggYRNrEIAc1Ys9gN7DV78"))
                .andExpect(status().isOk());
    }

}