package com.example.hair_care_tracker_api.controller;

import com.example.hair_care_tracker_api.entity.Routine;
import com.example.hair_care_tracker_api.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RoutineController.class)
class RoutineControllerTest {

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
    void getAllTest() throws Exception {
        Routine routine = new Routine("Rypacz", "desc", "effects", "2021-04-08T20:21:49.698195");
        when(routineRepository.findAllByUser(any())).thenReturn(Arrays.asList(routine));

        mockMvc.perform(get("/routines").header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbCIsInJvbGUiOiJ1c2VyIn0.h76xc7N0Ud7FhjJkqh3-RDzO_mqu1Bt8QpRM4RMrPV4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Rypacz")))
                .andExpect(jsonPath("$[0].user", nullValue()));
    }

    @Test
    void saveRoutineTest() {
        // TODO: Test put
    }
}