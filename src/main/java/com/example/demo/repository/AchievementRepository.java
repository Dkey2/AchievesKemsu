package com.example.demo.repository;

import com.example.demo.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
    <T> T findById(int achieveId, Class<T> type);
    <T> T findByName(String nameAchieve);
    <T> T findByCreator_IdAndId(int creatorId, int achieveId, Class<T> type);

    <T> List <T> findBy(Class<T> type);
    <T> List <T> findByIdIn(List<Integer> achievesAnotherStudentIds, Class<T> type);
    <T> List <T> findByCreator_Id(int creatorId, Class<T> type);
    <T> List <T> findByCategory_Id(int categoryId, Class<T> type);
    <T> List <T> findByStatusActive_Id(int statusId, Class<T> type);
    <T> List <T> findByIdNotInAndIdIn(List<Integer> receivedAchievesIds, List<Integer> idsReceivedAchievesAnotherStudent, Class<T> type);
    <T> List <T> findByCategory_IdAndStatusActive_Id(int categoryId, int statusId, Class<T> type);

    <T> List <T> findByStatusActive_IdNotIn(List<Integer> statusIds, Class<T> type);
    <T> List <T> findByStatusActive_IdNotInAndStatusActive_Id(List<Integer> statusIds, int statusAchieveId, Class<T> type);
    <T> List <T> findByStatusActive_IdNotInAndIdNotInAndStatusActive_Id(List<Integer> statusIds, List<Integer> receivedAchievesIds, int statusAchieveId, Class<T> type);

    <T> List <T> findByStatusActive_IdNotInAndCreator_Id(List<Integer> statusIds, int creatorId, Class<T> type);
    <T> List <T> findByStatusActive_IdNotInAndIdNotInAndCreator_Id(List<Integer> statusIds, List<Integer> achievesIds, int creatorId, Class<T> type);
    <T> List <T> findByStatusActive_IdNotInAndCategory_IdAndStatusActive_Id(List<Integer> statusIds, int categoryId, int statusAchieveId, Class<T> type);
    <T> List <T> findByStatusActive_IdNotInAndIdNotInAndCategory_IdAndStatusActive_Id(List<Integer> statusIds, List<Integer> receivedAchievesIds, int categoryId, int statusAchieveId, Class<T> type);
}
