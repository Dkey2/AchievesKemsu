package com.example.demo.repository;

import com.example.demo.entity.StatusActive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusActiveAchieveRepository extends JpaRepository<StatusActive, Integer> {
    StatusActive findById (int statusId);

    <T> List <T> findAllBy (Class<T> type);

    <T> T findById (int statusId, Class<T> type);
}
