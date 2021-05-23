package com.example.demo.repository;

import com.example.demo.entity.Group;
import com.example.demo.entity.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StreamRepository extends JpaRepository<Stream, Integer> {
    <T> List <T> findBy (Class<T> type);
    <T> List <T> findByInstitute_Id (int instituteId, Class<T> type);

    Stream findById (int streamId);
    Stream findByStreamName (String shortName);

    <T> T findFirstByOrderByIdDesc(Class<T> type);
}
