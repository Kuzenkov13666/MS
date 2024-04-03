package com.itm.space.backendresources.integration;

import com.itm.space.backendresources.BaseIntegrationTest;
import com.itm.space.backendresources.api.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest extends BaseIntegrationTest {
    @Test
    @WithMockUser(authorities = "ROLE_MODERATOR")
    void createExistUser() throws Exception {
        UserRequest userRequest = new UserRequest(
                "testUser",
                "test@user.ru",
                "testUser",
                "test",
                "test"
        );
        mvc.perform(requestWithContent(post("/api/users"), userRequest))
                .andExpect(status().is4xxClientError());
    }
    @Test
    @WithMockUser(authorities = "ROLE_MODERATOR")
    void createNotExistUser() throws Exception {
        UserRequest userRequest = new UserRequest(
                "testUser",
                "test@user.ru",
                "testUser",
                "test",
                "test"
        );
        mvc.perform(requestWithContent(post("/api/users"), userRequest))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_MODERATOR")
    void testGetByIdNotExist() throws Exception {
        UUID uuid = UUID.randomUUID();
        mvc.perform(requestToJson(get("/api/users/{id}", uuid)))
                .andExpect(status().is5xxServerError());

    }

    @Test
    @WithMockUser(authorities = "ROLE_MODERATOR")
    void testGetByIdExist() throws Exception {
        UUID uuid = UUID.fromString("b12a1406-ec4c-41ad-aa59-18268b409808");

        mvc.perform(requestToJson(get("/api/users/{id}", uuid)))
                .andExpect(status().isOk());

    }
    @Test
    @WithMockUser(authorities = "ROLE_MODERATOR")
    void testHello () throws Exception {
        mvc.perform(requestToJson(get("/api/users/hello")))
                .andExpect(status().isOk());
    }
}
