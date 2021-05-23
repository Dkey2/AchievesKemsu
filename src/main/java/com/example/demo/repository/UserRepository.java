package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    <T> T findById(Integer userId, Class<T> type);
    <T> T findByEmail(String email);

    <T> List <T> findBy(Class<T> type);
    <T> List <T> findByRole_Id(int roleId, Class<T> type);
    <T> List <T> findByStatusUser_Id(int statusId, Class<T> type);
    <T> List <T> findByRole_IdAndStatusUser_Id(int roleId, int statusId, Class<T> type);
    <T> List <T> findByLastNameContainingIgnoreCase(String substring, Class<T> type);
    <T> List <T> findByLastNameContainingIgnoreCaseAndRole_Id(String substring, int roleId, Class<T> type);
    <T> List <T> findByLastNameContainingIgnoreCaseAndStatusUser_Id(String substring, int statusId, Class<T> type);
    <T> List <T> findByLastNameContainingIgnoreCaseAndRole_IdAndStatusUser_Id(String substring, int roleId, int statusId, Class<T> type);
}
