package com.sprint_project.task_management.testing_service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint_project.task_management.controller.UserController;
import com.sprint_project.task_management.entity.User;
import com.sprint_project.task_management.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("pass");
        user.setEmail("test@example.com");
        user.setFullName("Test User");

        when(userService.createUser(any(User.class))).thenReturn(ResponseEntity.ok(user));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(ResponseEntity.ok(Arrays.asList(new User(), new User())));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById() throws Exception {
        when(userService.getUserById(1)).thenReturn(ResponseEntity.ok(new User()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUsersByEmailDomain() throws Exception {
        when(userService.getUsersByEmailDomain("example.com")).thenReturn(ResponseEntity.ok(Arrays.asList(new User())));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/email-domain/example.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchUsersByName() throws Exception {
        when(userService.searchUsersByName("John")).thenReturn(ResponseEntity.ok(Arrays.asList(new User())));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/search/John"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUsersWithMostTasks() throws Exception {
        when(userService.getUsersWithMostTasks()).thenReturn(ResponseEntity.ok(new User()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/most-tasks"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("pass");
        user.setEmail("test@example.com");
        user.setFullName("Test User");

        Map<String, Object> responseMap = Map.of(
            "code", "AUTH_SUCCESS",
            "message", "Authentication successful",
            "token", "dummy-jwt-token",
            "user", user
        );

        when(userService.authenticateUser("testuser", "pass"))
            .thenReturn(ResponseEntity.ok(responseMap));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/authenticate")
                .param("username", "testuser")
                .param("password", "pass"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUsersWithCompletedTasks() throws Exception {
        when(userService.getUsersWithCompletedTasks()).thenReturn(ResponseEntity.ok(Arrays.asList(new User())));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/completed-tasks"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setUsername("updateduser");
        user.setPassword("newpass");
        user.setEmail("updated@example.com");
        user.setFullName("Updated User");

        when(userService.updateUser(eq(1), any(User.class))).thenReturn(ResponseEntity.ok(user));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        when(userService.deleteUser(1)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/delete/1"))
                .andExpect(status().isOk());
    }
}
