package com.example.hair_care_tracker_api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class HairCareTrackerApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email\",\n" +
                        "  \"password\": \"$2b$12$SDL2oZdnyEKCOPbCyoC2vuBLF1w/2WbxA5JWn9lbx3xhCEUJTRWqG\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();
        Assertions.assertNotEquals(token, "");

        mockMvc.perform(get("/profile").header("authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

    @Test
    void signupTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"nickname": "funnynickname",
                          "email": "example@example.com",
                          "password": "passwordverystronk"}"""))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();
        Assertions.assertNotEquals(token, "");

        mockMvc.perform(get("/profile").header("authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void profileGetTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email\",\n" +
                        "  \"password\": \"$2b$12$SDL2oZdnyEKCOPbCyoC2vuBLF1w/2WbxA5JWn9lbx3xhCEUJTRWqG\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(get("/profile").header("authorization", "Bearer " + token))
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

    @Test
    void profilePutTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email\",\n" +
                        "  \"password\": \"$2b$12$SDL2oZdnyEKCOPbCyoC2vuBLF1w/2WbxA5JWn9lbx3xhCEUJTRWqG\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(put("/profile").header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "age": "10",
                          "length": "24",
                          "state": "Dyed",
                          "porosity": "High",
                          "curlType": "3c",
                          "thickness": "Medium",
                          "denseness": "Medium",
                          "problems": "problems"
                        }"""))
                .andExpect(status().isOk());

        mockMvc.perform(get("/profile").header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age", is("10")))
                .andExpect(jsonPath("$.length", is("24")))
                .andExpect(jsonPath("$.porosity", is("High")))
                .andExpect(jsonPath("$.state", is("Dyed")))
                .andExpect(jsonPath("$.curlType", is("3c")))
                .andExpect(jsonPath("$.thickness", is("Medium")))
                .andExpect(jsonPath("$.denseness", is("Medium")))
                .andExpect(jsonPath("$.problems", is("problems")))
                .andExpect(jsonPath("$.user", nullValue()));
    }

    @Test
    void productGetTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email\",\n" +
                        "  \"password\": \"$2b$12$SDL2oZdnyEKCOPbCyoC2vuBLF1w/2WbxA5JWn9lbx3xhCEUJTRWqG\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(get("/products").header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Dragon Wash")))
                .andExpect(jsonPath("$[0].brand", is("HairyTaleCosmetics")))
                .andExpect(jsonPath("$[0].capacity", is(250)))
                .andExpect(jsonPath("$[0].description", is("description")))
                .andExpect(jsonPath("$[0].ingredients[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].ingredients[0].name", is("Aqua")))
                .andExpect(jsonPath("$[0].ingredients[0].category", is("Humectant")))
                .andExpect(jsonPath("$[0].ingredients[1].name", is("Cetearyl Alcohol")))
                .andExpect(jsonPath("$[0].ingredients[1].category", is("Emolient")))
                .andExpect(jsonPath("$[0].users", nullValue()));
    }

    @Test
    void routineGetTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email\",\n" +
                        "  \"password\": \"$2b$12$SDL2oZdnyEKCOPbCyoC2vuBLF1w/2WbxA5JWn9lbx3xhCEUJTRWqG\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(get("/routines").header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Rypacz")))
                .andExpect(jsonPath("$[0].description", is("desc")))
                .andExpect(jsonPath("$[0].effects", is("effects")))
                .andExpect(jsonPath("$[0].date", is("2021-04-08T20:21:49.698195")))
                .andExpect(jsonPath("$[0].products[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].products[0].name", is("Liquid Gold")))
                .andExpect(jsonPath("$[1].name", is("Soft")))
                .andExpect(jsonPath("$[1].description", is("desc")))
                .andExpect(jsonPath("$[1].effects", is("effects")))
                .andExpect(jsonPath("$[1].date", is("2021-04-12T20:21:49.698195")))
                .andExpect(jsonPath("$[1].products[*]", hasSize(0)))
                .andExpect(jsonPath("$[0].user", nullValue()));
    }

    @Test
    void routinePutAddTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email\",\n" +
                        "  \"password\": \"$2b$12$SDL2oZdnyEKCOPbCyoC2vuBLF1w/2WbxA5JWn9lbx3xhCEUJTRWqG\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(put("/routines").header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "name",
                          "description": "desc",
                          "effects": "effects",
                          "date": "2021-04-13T20:21:49.698195",
                          "products": [],
                          "appUser": null
                        }
                        """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/routines").header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("name")))
                .andExpect(jsonPath("$[2].description", is("desc")))
                .andExpect(jsonPath("$[2].effects", is("effects")))
                .andExpect(jsonPath("$[2].date", is("2021-04-13T20:21:49.698195")))
                .andExpect(jsonPath("$[2].products[*]", hasSize(0)));
    }

    @Test
    void routinePutUpdateTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email\",\n" +
                        "  \"password\": \"$2b$12$SDL2oZdnyEKCOPbCyoC2vuBLF1w/2WbxA5JWn9lbx3xhCEUJTRWqG\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(put("/routines").header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                          {
                          "name": "Rypacz",
                          "description": "desc1",
                          "effects": "effects1",
                          "date": "2021-04-08T20:21:49.698195",
                          "products": [],
                          "appUser": null
                        }
                        """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/routines").header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Rypacz")))
                .andExpect(jsonPath("$[0].description", is("desc1")))
                .andExpect(jsonPath("$[0].effects", is("effects1")))
                .andExpect(jsonPath("$[0].date", is("2021-04-08T20:21:49.698195")))
                .andExpect(jsonPath("$[0].products[*]", hasSize(0)));
    }

    @Test
    void userProductsGetTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email\",\n" +
                        "  \"password\": \"$2b$12$SDL2oZdnyEKCOPbCyoC2vuBLF1w/2WbxA5JWn9lbx3xhCEUJTRWqG\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(get("/userproducts").header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Liquid Gold")))
                .andExpect(jsonPath("$[0].brand", is("HairyTaleCosmetics")))
                .andExpect(jsonPath("$[0].capacity", is(250)))
                .andExpect(jsonPath("$[0].description", is("description")))
                .andExpect(jsonPath("$[0].ingredients[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].ingredients[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].ingredients[0].name", is("Aqua")))
                .andExpect(jsonPath("$[0].ingredients[0].category", is("Humectant")))
                .andExpect(jsonPath("$[0].ingredients[1].name", is("Cetearyl Alcohol")))
                .andExpect(jsonPath("$[0].ingredients[1].category", is("Emolient")))
                .andExpect(jsonPath("$[0].users", nullValue()));
    }

    @Test
    void userProductsPutAddTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email\",\n" +
                        "  \"password\": \"$2b$12$SDL2oZdnyEKCOPbCyoC2vuBLF1w/2WbxA5JWn9lbx3xhCEUJTRWqG\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(put("/userproducts").header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        [
                          {
                            "name": "Liquid Gold",
                            "brand": "HairyTaleCosmetics",
                            "description": "description",
                            "capacity": 250,
                            "ingredients": [
                              {
                                "name": "Aqua",
                                "category": "Humectant"
                              },
                              {
                                "name": "Cetearyl Alcohol",
                                "category": "Emolient"
                              }
                            ],
                            "appUsers": null
                          },
                          {
                            "name": "Dragon Wash",
                            "brand": "HairyTaleCosmetics",
                            "description": "description",
                            "capacity": 250,
                            "ingredients": [
                              {
                                "name": "Aqua",
                                "category": "Humectant"
                              },
                              {
                                "name": "Cetearyl Alcohol",
                                "category": "Emolient"
                              }
                            ],
                            "appUsers": null
                          }
                        ]"""))
                .andExpect(status().isOk());

        mockMvc.perform(get("/userproducts").header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[1].name", is("Dragon Wash")))
                .andExpect(jsonPath("$[1].brand", is("HairyTaleCosmetics")))
                .andExpect(jsonPath("$[1].capacity", is(250)))
                .andExpect(jsonPath("$[1].description", is("description")))
                .andExpect(jsonPath("$[1].ingredients[*]", hasSize(2)))
                .andExpect(jsonPath("$[1].ingredients[0].name", is("Aqua")))
                .andExpect(jsonPath("$[1].ingredients[0].category", is("Humectant")))
                .andExpect(jsonPath("$[1].ingredients[1].name", is("Cetearyl Alcohol")))
                .andExpect(jsonPath("$[1].ingredients[1].category", is("Emolient")))
                .andExpect(jsonPath("$[1].users", nullValue()));

        mockMvc.perform(get("/products").header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(0)));
    }

    @Test
    void userProductsPutRemoveTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"email\",\n" +
                        "  \"password\": \"$2b$12$SDL2oZdnyEKCOPbCyoC2vuBLF1w/2WbxA5JWn9lbx3xhCEUJTRWqG\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString();

        mockMvc.perform(put("/userproducts").header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        [
                          {
                            "name": "Dragon Wash",
                            "brand": "HairyTaleCosmetics",
                            "description": "description",
                            "capacity": 250,
                            "ingredients": [
                              {
                                "name": "Aqua",
                                "category": "Humectant"
                              },
                              {
                                "name": "Cetearyl Alcohol",
                                "category": "Emolient"
                              }
                            ],
                            "appUsers": null
                          }
                        ]"""))
                .andExpect(status().isOk());

        mockMvc.perform(get("/userproducts").header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Dragon Wash")))
                .andExpect(jsonPath("$[0].brand", is("HairyTaleCosmetics")))
                .andExpect(jsonPath("$[0].capacity", is(250)))
                .andExpect(jsonPath("$[0].description", is("description")))
                .andExpect(jsonPath("$[0].ingredients[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].ingredients[0].name", is("Aqua")))
                .andExpect(jsonPath("$[0].ingredients[0].category", is("Humectant")))
                .andExpect(jsonPath("$[0].ingredients[1].name", is("Cetearyl Alcohol")))
                .andExpect(jsonPath("$[0].ingredients[1].category", is("Emolient")))
                .andExpect(jsonPath("$[0].users", nullValue()));

        mockMvc.perform(get("/products").header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)));
    }
}