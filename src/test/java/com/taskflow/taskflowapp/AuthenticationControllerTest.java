package com.taskflow.taskflowapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskflow.taskflowapp.dto.LoginRequest;
import com.taskflow.taskflowapp.model.Role;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.RoleRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Before
    public void setUp() {
//        Optional<User> existingUser = userRepository.findByUsername("admin");
//        if (existingUser.isEmpty()) {
//            String encodedPassword = passwordEncoder.encode("admin");
//            Role userRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role USER not found!"));
//            User admin = new User("admin", encodedPassword, userRole);
//            userRepository.save(admin);
//        }
    }

    /*
    Test sprawdza:
    - Czy logowanie sie powiodlo (status 200)
    - czy zawiera token
    */
    @Test
    public void testLoginWhenCredentialsAreValid() throws Exception {
        Optional<User> existingUser = userRepository.findByUsername("admin");
        if (existingUser.isEmpty()) {
            String encodedPassword = passwordEncoder.encode("admin");
            Role userRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role USER not found!"));
            User admin = new User("admin", encodedPassword, userRole);
            userRepository.save(admin);
        }
        LoginRequest loginRequest = new LoginRequest("admin", "admin");

        mockMvc.perform(post("/login") // Symulowanie zapytania HTTP
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void testLoginWhenPasswordIsNotValid() throws Exception {
        Optional<User> existingUser = userRepository.findByUsername("admin");
        if (existingUser.isEmpty()) {
            String encodedPassword = passwordEncoder.encode("admin");
            Role userRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role USER not found!"));
            User admin = new User("admin", encodedPassword, userRole);
            userRepository.save(admin);
        }
        LoginRequest loginRequest = new LoginRequest("admin", "adminNotValid");

        mockMvc.perform(post("/login") // Symulowanie zapytania HTTP
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginWhenCredentialsAreNotValid() throws Exception {
        Optional<User> existingUser = userRepository.findByUsername("admin");
        if (existingUser.isEmpty()) {
            String encodedPassword = passwordEncoder.encode("admin");
            Role userRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role USER not found!"));
            User admin = new User("admin", encodedPassword, userRole);
            userRepository.save(admin);
        }
        LoginRequest loginRequest = new LoginRequest("adminNotValid", "adminNotValid");

        mockMvc.perform(post("/login") // Symulowanie zapytania HTTP
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void testRegisterWhenUserDoesntExist() throws Exception {
        Optional<User> existingUser = userRepository.findByUsername("admin");
        if (existingUser.isPresent()) {
            userRepository.delete(existingUser.get());
        }
        LoginRequest loginRequest = new LoginRequest("admin", "admin");
        mockMvc.perform(post("/register") // Symulowanie zapytania HTTP
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isCreated()); // 201
    }

    @Test
    public void testRegisterWhenUserExist() throws Exception {
        Optional<User> existingUser = userRepository.findByUsername("admin");
        if (existingUser.isEmpty()) {
            String encodedPassword = passwordEncoder.encode("admin");
            Role userRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role USER not found!"));
            User admin = new User("admin", encodedPassword, userRole);
            userRepository.save(admin);
        }
        LoginRequest loginRequest = new LoginRequest("admin", "admin");
        mockMvc.perform(post("/register") // Symulowanie zapytania HTTP
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isConflict()); // 409
    }
}
