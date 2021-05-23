package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AchieveService {
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StatusActiveAchieveRepository statusActiveAchieveRepository;
    @Autowired
    private RewardRepository rewardRepository;
    @Autowired
    private AchievementReceivedRepository achievementReceivedRepository;
    @Autowired
    private UserRepository userRepository;


    //Создание нового достижения в системе
    public Achievement createNewAchievement(Achievement achievement, int userId, int categoryId, int rewardId) {
        User user = userRepository.findById(userId, User.class);
        achievement.setCreatorOfAchieve(user);
        Reward reward = rewardRepository.findById(rewardId);
        achievement.setRewardOfAchieve(reward);
        Category category = categoryRepository.findById(categoryId);
        achievement.setCategoryOfAchieve(category);

        int maxId = achievementRepository.findFirstByOrderByIdDesc(Achievement.class).getIdAchieve();
        achievement.setIdAchieve(maxId+1);
        StatusActive statusActive = statusActiveAchieveRepository.findById(1);
        achievement.setStatusActiveOfAchieve(statusActive);
        achievementRepository.save(achievement);
        return achievement;
    }

    //Изменение статуса награды на true и сохранение в базе
    public ResponseEntity changeStatusReward(AchievementOfStudent ach) {
        ach.setStatusRewardForAchieveOfStudent(true);
        achievementReceivedRepository.save(ach);
        return ResponseEntity.status(HttpStatus.OK).body("Награда успешно получена");
    }



    //Получение категории по ее id
    public Category getCategory(int categoryId) {
        return categoryRepository.findById(categoryId);
    }

    //Сохранение достижения
    public Achievement saveAchievement(Achievement achievement) {
        return achievementRepository.save(achievement);
    }

    //Получение статуса активности по его id
    public StatusActive getStatusActive(int statusId) {
        return statusActiveAchieveRepository.findById(statusId);
    }


    //Сохранение достижения студента
    public void saveAchievementOfStudent(AchievementOfStudent achievementOfStudent) {
        achievementReceivedRepository.save(achievementOfStudent);
    }



    //Получение статуса активности по его id
    public <T> T getStatusActiveById(int statusId, Class <T> type) {
        return statusActiveAchieveRepository.findById(statusId, type);
    }

    //Получение достижения, принадлежащего студенту
    public <T> T getAchieveForStudent(int achieveId, int studentId, Class<T> type) {
        return achievementReceivedRepository.findByIdAndStudent_Id(achieveId, studentId, type);
    }

    //Получение достижения по его названию
    public <T> T getAchieveByName(String achieveName) {
        return achievementRepository.findByName(achieveName);
    }

    //Получение достижения по его id
    public <T> T getAchieveById(int achieveId, Class<T> type) {
        return achievementRepository.findById(achieveId, type);
    }

    //Получение достижения, созданного пользователем по id достижения и пользователя
    public <T> T getCreatedAchieveOfStudent(int creatorId, int achieveId, Class<T> type) {
        return achievementRepository.findByCreator_IdAndId(creatorId, achieveId, type);
    }




    //Получение списка всех статусов активности
    public <T> List <T> getAllStatus(Class<T> type) {
        return statusActiveAchieveRepository.findAllBy(type);
    }

    //Получение списка всех достижений в системе
    public <T> List <T> getAllAchieve(Class<T> type) {
        return achievementRepository.findBy(type);
    }

    //Получение списка всех категорий
    public <T> List <T> getAllCategory(Class<T> type) {
        return categoryRepository.findBy(type);
    }

    //Получение списка достижений по id статуса активности
    public <T> List <T> getAchieveStatus(int statusId, Class<T> type) {
        return achievementRepository.findByStatusActive_Id(statusId, type);
    }

    //Получение списка достижений, по id пользователя-создателя
    public <T> List <T> getAchieveByCreator(Integer creatorId, Class<T> type) {
        return achievementRepository.findByCreator_Id(creatorId, type);
    }

    //Получение списка достижений по id категории
    public <T> List <T> getCategoryOfAchieve(int categoryId, Class<T> type) {
        return achievementRepository.findByCategory_Id(categoryId, type);
    }

    //Получение списка всех достижений для студента
    public <T> List <T> getAllAchievesForStudent(List<Integer> statusIds, Class<T> type) {
        return achievementRepository.findByStatusActive_IdNotIn(statusIds, type);
    }

    //Получение списка полученных студентом достижений по id студента и статуса активности
    public <T> List <T> getAchievementsReceived(int studentId, int statusAchieveId, Class<T> type) {
        return achievementReceivedRepository.findByStudent_IdAndAchievement_StatusActive_Id(studentId, statusAchieveId, type);
    }

    //Получение списка достижений для студента по id категории и статуса активности
    public <T> List <T> getAchieveStatusCategory(int categoryId, int statusId, Class<T> type) {
        return achievementRepository.findByCategory_IdAndStatusActive_Id(categoryId, statusId, type);
    }

    //Получение списка неполученных авторизованным студентом достижений по id статуса активности
    public <T> List <T> getAchievementsUnreceivedAll(List<Integer> statusIds, int statusAchieveId, Class<T> type) {
        return achievementRepository.findByStatusActive_IdNotInAndStatusActive_Id(statusIds, statusAchieveId, type);
    }

    //Получение списка полученных студентом достижений по id студента
    public <T> List <T> getReceivedAchievesForStudent(int studentId, Class<T> type) {
        return achievementReceivedRepository.findByStudent_Id(studentId, type);
    }

    //Получение списка полученных достижений в определенной категории
    public <T> List <T> getAchievementsReceivedCategory(int categoryId, int studentId, int statusAchieveId, Class<T> type) {
        return achievementReceivedRepository.findByAchievement_Category_IdAndStudent_IdAndAchievement_StatusActive_Id(categoryId, studentId, statusAchieveId, type);
    }

    //Получение списка полученных достижений созданных авторизованным или другим студентом
    public <T> List <T> getReceivedAchieveCreatedStudent(List<Integer> statusIds, int studentId, int creatorId, Class<T> type) {
        return achievementReceivedRepository.findByAchievement_StatusActive_IdNotInAndStudent_IdAndAchievement_Creator_Id(statusIds, studentId, creatorId, type);
    }

    //Получение списка неполученных достижений в определенной категории
    public <T> List <T> getAchievementsUnreceivedCategory(List<Integer> statusIds, int categoryId, int statusAchieveId, Class<T> type) {
        return achievementRepository.findByStatusActive_IdNotInAndCategory_IdAndStatusActive_Id(statusIds, categoryId, statusAchieveId, type);
    }

    //Получение списка неполученных достижений без полученных
    public <T> List <T> getAchievementsUnreceivedWithoutReceived(List<Integer> statusIds, List<Integer> receivedAchievesIds, int statusAchieveId, Class<T> type) {
        return achievementRepository.findByStatusActive_IdNotInAndIdNotInAndStatusActive_Id(statusIds, receivedAchievesIds, statusAchieveId, type);
    }

    //Получение списка неполученных достижений созданных авторизованным или другим студентом
    public <T> List <T> getAchievementsUnreceivedAndCreatedStudent(List<Integer> statusIds, int creatorId, Class<T> type) {
        return achievementRepository.findByStatusActive_IdNotInAndCreator_Id(statusIds, creatorId, type);
    }

    //Получение списка полученных достижений авторизованным студентом среди полученных достижений другого студента
    public <T> List <T> getReceivedAchievesFromAchievesAnotherStudent(int studentId, List<Integer> receivedAchievesAnotherStudentIds, Class<T> type) {
        return achievementReceivedRepository.findByStudent_IdAndIdIn(studentId, receivedAchievesAnotherStudentIds, type);
    }

    //Получение списка неполученных достижений авторизованным студентом, среди полученных достижений другого студента
    public <T> List <T> getUnreceivedAchievesFromAchievesAnotherStudent(List<Integer> achievesIds, List<Integer> achievesAnotherStudentIds, Class<T> type) {
        return achievementRepository.findByIdNotInAndIdIn(achievesIds, achievesAnotherStudentIds, type);
    }

    //Получение списка неполученных достижений авторизованным студентом, среди полученных достижений другого студента, если список полученных им достижений из списка
    // достижений студента, которого мы просматриваем, пуст
    public <T> List <T> getUnreceivedAchievesFromAchievesAnotherStudentWithNullReceivedAchieves(List<Integer> achievesAnotherStudentIds, Class<T> type) {
        return achievementRepository.findByIdIn(achievesAnotherStudentIds, type);
    }

    //Получение списка неполученных достижений без полученных в определенной категории
    public <T> List <T> getAchievementsUnreceivedCategoryWithoutReceived(List<Integer> statusIds, List<Integer> receivedAchievesIds, int categoryId, int statusAchieveId, Class<T> type) {
        return achievementRepository.findByStatusActive_IdNotInAndIdNotInAndCategory_IdAndStatusActive_Id(statusIds, receivedAchievesIds, categoryId, statusAchieveId, type);
    }

    //Получение списка неполученных достижений без полученных из достижений, созданных авторизованным или другим студентом
    public <T> List <T> getAchievementsUnreceivedAndCreatedStudentWithoutReceived(List<Integer> statusIds, List<Integer> achievesIds, int creatorId, Class<T> type) {
        return achievementRepository.findByStatusActive_IdNotInAndIdNotInAndCreator_Id(statusIds, achievesIds, creatorId, type);
    }


}
