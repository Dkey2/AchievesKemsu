package com.example.demo.repository;

import com.example.demo.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Integer> {
    <T> T findById (int fileId, Class<T> type);

    <T> List <T> findByListFile_Id (int listFileId, Class<T> type);
}
