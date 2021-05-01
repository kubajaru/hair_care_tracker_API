package com.example.hair_care_tracker_api.controller;

import com.example.hair_care_tracker_api.entity.AppUser;
import com.example.hair_care_tracker_api.repository.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
class SignUpControllerTest {

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
    void signUpTest() throws Exception {
        when(userRepository.findByEmail("email")).thenReturn(null);

        when(userRepository.findByNickname("nickname")).thenReturn(null);

        MvcResult result = mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_JSON)
                .content("{\"nickname\": \"nickname\",\n" +
                        "  \"email\": \"email\",\n" +
                        "  \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        Assertions.assertNotEquals(token, "");

        Claims claims = Jwts.parser().setSigningKey("oCIZHIEqSmBcO1O").parseClaimsJws(token).getBody();

        Assertions.assertEquals(claims.getSubject(), "email");
    }

    @Test
    void signUpTestExistingEmail() throws Exception {
        when(userRepository.findByEmail("email")).thenReturn(new AppUser("nickname", "email", "password"));

        when(userRepository.findByNickname("nickname")).thenReturn(null);

        mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_JSON)
                .content("{\"nickname\": \"nickname\",\n" +
                        "  \"email\": \"email\",\n" +
                        "  \"password\": \"password\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUpTestExistingNickname() throws Exception {
        when(userRepository.findByEmail("email")).thenReturn(null);

        when(userRepository.findByNickname("nickname")).thenReturn(new AppUser("nickname", "email", "password"));

        mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_JSON)
                .content("{\"nickname\": \"nickname\",\n" +
                        "  \"email\": \"email\",\n" +
                        "  \"password\": \"password\"}"))
                .andExpect(status().isConflict());
    }
}