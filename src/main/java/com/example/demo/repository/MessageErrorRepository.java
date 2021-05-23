package com.example.demo.repository;

import com.example.demo.entity.MessageError;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageErrorRepository extends JpaRepository<MessageError, Integer> {

    <T> T findById (int errorId, Class<T> type);
    <T> T findByStudent_IdAndId (int studentId, int messageErrorId, Class<T> type);

    <T> List <T> findBy (Class<T> type);
    <T> List <T> findByStudent_Id (int studentId, Class<T> type);
    <T> List <T> findByStatusMessageError_Id (int statusId, Class<T> type);
}
