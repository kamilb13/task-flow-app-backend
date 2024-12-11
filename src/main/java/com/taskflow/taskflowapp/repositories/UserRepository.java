package com.taskflow.taskflowapp.repositories;

import com.taskflow.taskflowapp.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}