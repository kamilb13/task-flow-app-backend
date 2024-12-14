package com.taskflow.taskflowapp.repositories;

import com.taskflow.taskflowapp.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Object> findByName(String user);
}
