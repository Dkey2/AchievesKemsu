package com.example.demo.repository;

import com.example.demo.entity.FormOfEducation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormEducationRepository extends JpaRepository<FormOfEducation, Integer> {
    <T> List <T> findBy (Class<T> type);

    FormOfEducation findById (int formEducationId);
}
