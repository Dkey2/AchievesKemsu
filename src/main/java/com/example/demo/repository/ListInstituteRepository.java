package com.example.demo.repository;

import com.example.demo.entity.ListInstitute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListInstituteRepository extends JpaRepository<ListInstitute, Integer> {
    ListInstitute findById (int listInstituteId);
    ListInstitute findFirstByOrderByIdAsc();

    <T> List <T> findBy(Class<T> type);
}
