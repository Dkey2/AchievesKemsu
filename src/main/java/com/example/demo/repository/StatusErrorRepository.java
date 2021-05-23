package com.example.demo.repository;

import com.example.demo.entity.StatusMessageError;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusErrorRepository extends JpaRepository<StatusMessageError, Integer> {
    <T> T findById (int statusId, Class<T> type);
    <T> T findByStatusErrorName (String errorName, Class<T> type);

    <T> List <T> findBy (Class<T> type);
}
