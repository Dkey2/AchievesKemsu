package com.example.demo.repository;

import com.example.demo.entity.StatusRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusProofRepository extends JpaRepository<StatusRequest, Integer> {
    <T> T findById(int id, Class<T> type);
}

