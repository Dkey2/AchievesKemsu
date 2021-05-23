package com.example.demo.repository;

import com.example.demo.entity.Ban;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanRepository extends JpaRepository<Ban, Integer> {
    Ban findById (int banId);
    Ban findByUserBanned_Id (int userId);
    Ban findFirstByOrderByIdDesc();
}
