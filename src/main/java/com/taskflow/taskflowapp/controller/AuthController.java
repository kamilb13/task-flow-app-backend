package com.taskflow.taskflowapp.controller;

import com.taskflow.taskflowapp.security.JwtUtil;
import com.taskflow.taskflowapp.dto.LoginRequest;
import com.taskflow.taskflowapp.dto.LoginResponse;
import com.taskflow.taskflowapp.model.Role;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.RoleRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserRepository userRepository, RoleRepository roleRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest loginRequest) {
        Optional<User> pendingUser = userRepository.findByUsername(loginRequest.getUsername());
        if (pendingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already in use");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(loginRequest.getPassword());
        //Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role USER not found"));
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role USER not found"));
        User newUser = new User(loginRequest.getUsername(), encodedPassword, userRole);
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully" + newUser.toString());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> pendingUser = userRepository.findByUsername(loginRequest.getUsername());
        if (pendingUser.isPresent()) {
            User user = pendingUser.get();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername());
                return ResponseEntity.ok(new LoginResponse(token));
//                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @PostMapping("/assign-role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> assignRoleToUser(@RequestParam String username, @RequestParam String roleName) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);
        userRepository.save(user);
        return ResponseEntity.ok("Role " + roleName + " assigned to user " + username);
    }
}
