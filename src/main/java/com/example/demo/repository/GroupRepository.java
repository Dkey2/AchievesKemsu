package com.example.demo.repository;

import com.example.demo.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    Group findById (int groupId);
    Group findByGroupName (String groupName);

    <T> T findFirstByOrderByIdDesc (Class<T> type);

    <T> List <T> findBy (Class<T> type);
    <T> List <T> findByStream_Id (int streamId, Class<T> type);
}
