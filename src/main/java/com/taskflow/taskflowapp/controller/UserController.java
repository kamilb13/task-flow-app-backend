package com.taskflow.taskflowapp.controller;

import com.taskflow.taskflowapp.dto.LoginRequest;
import com.taskflow.taskflowapp.model.Role;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.RoleRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/users")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("secure")
    public String secure() {
        return "secure";
    }

    // For test
    @PostMapping("/add-test-admin")
    public ResponseEntity<?> addAdmin() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("admin");
        Role userRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role USER not found"));
        User newUser = new User("admin", encodedPassword, userRole);
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin created" + newUser.toString());
    }
}
