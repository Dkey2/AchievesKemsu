package com.example.demo.repository;

import com.example.demo.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
    <T> List <T> findByOrderByIdAsc(Class<T> type);

    <T> T findById(int id, Class<T> type);
}
