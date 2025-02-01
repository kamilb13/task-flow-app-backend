package com.taskflow.taskflowapp.controller;

import com.taskflow.taskflowapp.dto.UsersResponse;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.UserRepository;
import com.taskflow.taskflowapp.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {
    //    private final String uploadDir = "uploads/";
    private final String uploadDir = "./";

    UserRepository userRepository;
    private final FileUploadService fileUploadService;

    @Autowired
    public UserController(UserRepository userRepository, FileUploadService fileUploadService) {
        this.userRepository = userRepository;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/users")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UsersResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map((user) -> new UsersResponse(user.getId(), user.getUsername()))
                .toList();
    }

    @PostMapping("/{userId}/avatar")
    public ResponseEntity<?> uploadAvatar(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            String avatarUrl = fileUploadService.uploadFile(file);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setAvatarUrl(avatarUrl);
            userRepository.save(user);
            return ResponseEntity.ok().body(Map.of("avatarUrl", avatarUrl));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/avatar")
    public ResponseEntity<?> getAvatar() {
        return ResponseEntity.ok().body("Hello World");
    }

    @GetMapping("/get-avatar/{userId}")
    public ResponseEntity<Resource> getAvatar(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        String avatarFilename = userOptional.get().getAvatarUrl(); // np. "default_avatar.jpeg"
//        Path filePath = Paths.get(uploadDir).resolve(avatarFilename).normalize();
        Path filePath = Paths.get(uploadDir, avatarFilename).toAbsolutePath().normalize();
        System.out.println(filePath.toUri());
        System.out.println(filePath);
        File file = filePath.toFile();
        System.out.println("Sprawdzam plik: " + file.getAbsolutePath());
        System.out.println("Czy plik istnieje? " + file.exists());
        try {
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                        .body(resource);
            } else {
                System.out.println("nie ma zdjecia");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}