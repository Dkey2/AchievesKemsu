package com.example.demo.repository;

import com.example.demo.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstituteRepository extends JpaRepository<Institute, Integer> {
    Institute findById (int instituteId);

    <T> List <T> findBy (Class<T> type);
    <T> List <T> findByListInstitute_Id (int listInstituteId, Class<T> type);
}
