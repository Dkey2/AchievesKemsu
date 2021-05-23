package com.example.demo.repository;

import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findById (int roleId);
    Role findByName (String roleName);

    <T> List <T> findBy (Class<T> type);
}
