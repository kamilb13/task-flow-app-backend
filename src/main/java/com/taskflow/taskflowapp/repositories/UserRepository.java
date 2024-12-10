package com.taskflow.taskflowapp.repositories;

import com.taskflow.taskflowapp.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
