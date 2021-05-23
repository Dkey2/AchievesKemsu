package com.example.demo.repository;

import com.example.demo.entity.AchievementOfStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementReceivedRepository extends JpaRepository<AchievementOfStudent, Integer> {
    <T> T findByIdAndStudent_Id(int achieveId, int studentId, Class<T> type);
    <T> T findFirstByOrderByIdDesc (Class<T> type);

    <T> List <T> findByAchievement_Category_IdAndStudent_IdAndAchievement_StatusActive_Id (int categoryId, int studentId, int statusAchieveId, Class<T> type);
    <T> List <T> findByAchievement_StatusActive_IdNotInAndStudent_IdAndAchievement_Creator_Id (List<Integer> statusIds, int studentId, int creatorId, Class<T> type);

    <T> List <T> findByStudent_Id (int studentId, Class<T> type);
    <T> List <T> findByStudent_IdAndAchievement_StatusActive_Id (int studentId, int statusAchieveId, Class<T> type);
    <T> List <T> findByStudent_IdAndIdIn (int studentId, List<Integer> receivedAchievesAnotherStudentIds, Class<T> type);
}
