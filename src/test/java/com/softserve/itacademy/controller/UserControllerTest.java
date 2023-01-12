package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UserControllerTest {

    public static MultiValueMap<String, String> invalidUserMap = new LinkedMultiValueMap<>();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepositoryMock;

    @BeforeAll
    public static void config() {
        invalidUserMap.add("firstName", "Mykhailo");
        invalidUserMap.add("lastName", "Lolotailo");
        invalidUserMap.add("password", "1Coolpassword");
        invalidUserMap.add("email", "kolotailo@mail.com");
        invalidUserMap.add("roleId", "1");
    }

    @Test
    public void createInvalidUserTest() throws Exception {
        when(userRepositoryMock.save(any())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/create").params(invalidUserMap))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.model().attribute("code", "500" + " / " + "Internal Server Error"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "User cannot be null"));
    }

    @Test
    public void updateInvalidUserTest() throws Exception {
        User user = new User();
        user.setRole(new Role(2L, "ADMIN", Collections.emptyList()));
        user.setId(4);
        when(userRepositoryMock.findById(any())).thenReturn(Optional.of(user));
        when(userRepositoryMock.save(any())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/4/update").params(invalidUserMap))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.model().attribute("code", "500" + " / " + "Internal Server Error"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "User cannot be null"));
    }

    @Test
    public void findUnexistedObject() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/users/400/read").params(invalidUserMap))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.model().attribute("code", "404" + " / " + "Not Found"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "User with id 400 not found"));
    }

    @Test
    public void deleteUnexistedObject() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/users/400/delete").params(invalidUserMap))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.model().attribute("code", "404" + " / " + "Not Found"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "User with id 400 not found"));
    }
}