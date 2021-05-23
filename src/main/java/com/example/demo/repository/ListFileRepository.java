package com.example.demo.repository;

import com.example.demo.entity.ListFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListFileRepository extends JpaRepository<ListFile, Integer> {
    <T> T findById (int listFileId, Class<T> type);
    <T> T findFirstByOrderByIdDesc (Class<T> type);
}
