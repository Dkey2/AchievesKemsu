package com.example.demo.repository;

import com.example.demo.entity.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeratorRepository extends JpaRepository<Moderator, Integer> {
    <T> List <T> findBy (Class<T> type);

    <T> T findById (int moderatorId, Class<T> type);
}
