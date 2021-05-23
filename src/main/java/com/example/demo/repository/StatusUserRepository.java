package com.example.demo.repository;

import com.example.demo.entity.StatusUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusUserRepository extends JpaRepository<StatusUser, Integer> {
    StatusUser findByStatusUser (String name);
}
