package com.taskflow.taskflowapp;

import com.taskflow.taskflowapp.model.Role;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.RoleRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminInitializer(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initializeAdmin() {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode("admin");
//        Role userRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role ADMIN not found"));
//        if (!userRepository.existsByUsername("admin")) {
//            User newUser = new User("admin", encodedPassword, userRole);
//            userRepository.save(newUser);
//            System.out.println("Admin user created: " + newUser.toString());
//        } else {
//            System.out.println("Admin user already exists.");
//        }

//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode("admin");
//        Role userRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role USER not found"));
//        User newUser = new User("admin", encodedPassword, userRole);
//        userRepository.save(newUser);
//        //return ResponseEntity.status(HttpStatus.CREATED).body("Admin created" + newUser.toString());
    }
}

