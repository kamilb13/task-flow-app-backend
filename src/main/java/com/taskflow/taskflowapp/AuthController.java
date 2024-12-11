package com.taskflow.taskflowapp;

import com.taskflow.taskflowapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {
    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest loginRequest) {
        Optional<User> pendingUser = userRepository.findByUsername(loginRequest.getUsername());
        System.out.println(pendingUser.isPresent());
        if(pendingUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already in use");
        }
        User newUser = new User(loginRequest.getUsername(), loginRequest.getPassword());
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully" + newUser.toString());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> pendingUser = userRepository.findByUsername(loginRequest.getUsername());
        System.out.println(pendingUser);
        if (pendingUser.isPresent()) {
            User user = pendingUser.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
}
