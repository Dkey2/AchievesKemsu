package com.example.demo.repository;

import com.example.demo.entity.CodeReset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeResetRepository extends JpaRepository<CodeReset, Integer> {
    CodeReset findByUser_Id (int userId);
}
