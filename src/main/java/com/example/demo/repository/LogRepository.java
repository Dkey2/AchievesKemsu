package com.example.demo.repository;

import com.example.demo.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Integer> {
    <T> T findById(int logId, Class<T> type);

    <T> List <T> findByOrderByChangeDateDesc (Class <T> type);
    <T> List <T> findByUser_Role_IdOrderByChangeDateDesc (int roleId, Class <T> type);
    <T> List <T> findByOperation_IdOrderByChangeDateDesc (int operationId, Class <T> type);
    <T> List <T> findByUser_Role_IdAndOperation_IdOrderByChangeDateDesc (int roleId, int operationId, Class <T> type);
}
