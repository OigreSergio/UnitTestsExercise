package com.OIgres.UnitTestExercise;

import com.OIgres.UnitTestExercise.services.ServicesUser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class TestUserController {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ServicesUser service;
    @Autowired
    private ObjectMapper mapper;

    UserEntity userTest = new UserEntity(1L, "Alessio", "Delle Donne");
    UserEntity userTest2 = new UserEntity(2L, "Sio", "Worro");

    @Test
    public void testCreateUser() throws Exception {
        String json = mapper.writeValueAsString(userTest);
        MvcResult result = this.mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        UserEntity response = mapper.readValue(result.getResponse().getContentAsString(), UserEntity.class);
        assertEquals(userTest.getId(), response.getId());
    }

    @Test
    public void testShowAllUsers() throws Exception {
        service.create(userTest);
        service.create(userTest2);

        MvcResult result = this.mvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<UserEntity> response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<UserEntity>>() {
        });
        assertFalse(response.isEmpty());
    }

    @Test
    public void testShowASingelUser() throws Exception {
        service.create(userTest);

        MvcResult result = this.mvc.perform(get("/users/{id}", userTest.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alessio"))
                .andExpect(jsonPath("$.surname").value("Delle Donne"))
                .andReturn();

        UserEntity response = mapper.readValue(result.getResponse().getContentAsString(), UserEntity.class);
        assertEquals(userTest.getId(), response.getId());
    }

    @Test
    public void testUpdateUser() throws Exception {
        service.create(userTest);
        String json = mapper.writeValueAsString(userTest2);
        MvcResult result = this.mvc.perform(put("/users/{id}", userTest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sio"))
                .andExpect(jsonPath("$.surname").value("Worro"))
                .andReturn();

        UserEntity response = mapper.readValue(result.getResponse().getContentAsString(), UserEntity.class);
        assertEquals(response.getId(), userTest.getId());
    }

    @Test
    public void testDeleteUser() throws Exception {
        service.create(userTest);

        MvcResult result = this.mvc.perform(delete("/users/{id}", userTest.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()).andReturn();
        List<UserEntity>response = service.searchAll();
        assertFalse(response.contains(userTest));
    }
}
