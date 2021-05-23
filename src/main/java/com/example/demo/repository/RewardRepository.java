package com.example.demo.repository;

import com.example.demo.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, Integer> {
    Reward findById(int rewardId);
    <T>List<T> findBy(Class<T> type);
}
