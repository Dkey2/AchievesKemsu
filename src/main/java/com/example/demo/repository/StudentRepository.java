package com.example.demo.repository;

import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    /*  Топ 10 студентов с фильтрами  */
    <T> List <T> findFirst10ByUser_StatusUser_StatusUserNotInOrderByScoreDesc                       (                       List<String> status, Class<T> type);
    <T> List <T> findFirst10ByGroup_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc            (int groupId,           List<String> status, Class<T> type);
    <T> List <T> findFirst10ByStream_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc           (int streamId,          List<String> status, Class<T> type);
    <T> List <T> findFirst10ByInstitute_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc        (int instituteId,       List<String> status, Class<T> type);
    <T> List <T> findFirst10ByFormOfEducation_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc  (int formOfEducationId, List<String> status, Class<T> type);

    /*  Топ 50 студентов с фильтрами  */
    <T> List <T> findFirst50ByUser_StatusUser_StatusUserNotInOrderByScoreDesc                       (                       List<String> status, Class<T> type);
    <T> List <T> findFirst50ByGroup_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc            (int groupId,           List<String> status, Class<T> type);
    <T> List <T> findFirst50ByStream_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc           (int streamId,          List<String> status, Class<T> type);
    <T> List <T> findFirst50ByInstitute_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc        (int instituteId,       List<String> status, Class<T> type);
    <T> List <T> findFirst50ByFormOfEducation_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc  (int formOfEducationId, List<String> status, Class<T> type);

    /*  Топ 100 студентов с фильтрами  */
    <T> List <T> findFirst100ByUser_StatusUser_StatusUserNotInOrderByScoreDesc                      (                       List<String> status, Class<T> type);
    <T> List <T> findFirst100ByGroup_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc           (int groupId,           List<String> status, Class<T> type);
    <T> List <T> findFirst100ByStream_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc          (int streamId,          List<String> status, Class<T> type);
    <T> List <T> findFirst100ByInstitute_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc       (int instituteId,       List<String> status, Class<T> type);
    <T> List <T> findFirst100ByFormOfEducation_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc (int formOfEducationId, List<String> status, Class<T> type);

    /*  Поиск конкрентного студента  */
    <T> T findById(int studentId, Class<T> type);
    Student findByUser_Id(int userId);

    /*  Поиск по подстроке  */
    <T> List <T> findByUser_LastNameContainingIgnoreCase(String substring, Class<T> type);
    <T> List <T> findByInstitute_ListInstitute_IdAndUser_LastNameContainingIgnoreCase(int listInstituteId, String substring, Class<T> type);
    <T> List <T> findByUser_LastNameContainingIgnoreCaseAndUser_StatusUser_StatusUserNotIn(String lastName, List<String> status, Class<T> type);
    <T> List <T> findByUser_LastNameContainingIgnoreCaseAndUser_StatusUser_IdOrderByUser_LastNameAsc(String substring, int status, Class<T> type);
    <T> List <T> findByInstitute_ListInstitute_IdAndUser_LastNameContainingIgnoreCaseAndUser_StatusUser_IdOrderByUser_LastNameAsc(int listInstituteId, String substring, int status, Class<T> type);

    <T> List <T> findByOrderByUser_LastNameAsc(Class<T> type);
    <T> List <T> findByUser_StatusUser_IdOrderByUser_LastNameAsc(int statusUserId, Class<T> type);
    <T> List <T> findByInstitute_ListInstitute_IdOrderByUser_LastNameAsc(int listInstituteId, Class<T> type);
    <T> List <T> findByInstitute_ListInstitute_IdAndUser_StatusUser_IdOrderByUser_LastNameAsc(int listInstituteId, int statusUserId, Class<T> type);
}
