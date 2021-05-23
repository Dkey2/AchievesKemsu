package com.example.demo.service;

import com.example.demo.entity.ProofAchieve;
import com.example.demo.entity.StatusRequest;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.ProofRepository;
import com.example.demo.repository.StatusProofRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProofService {
    @Autowired
    private ProofRepository proofRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private StatusProofRepository statusProofRepository;

    //Создание нового подтверждения достижения
    public void newProof(ProofAchieve proofAchieve) {
        StatusRequest statusRequest = statusProofRepository.findById(4, StatusRequest.class);
        proofAchieve.setStatusRequestProof(statusRequest);
        proofRepository.save(proofAchieve);
    }

    //Сохранение подтверждения достижения
    public void saveProof(ProofAchieve proofAchieve) { proofRepository.save(proofAchieve);
    }



    //Получение подтверждения достижения по его id
    public <T> T getProof(int proofId, Class<T> type) {
        return proofRepository.findById(proofId, type);
    }

    //Получение подтверждения для определенного студента
    public <T> T getProofStudent(int studentId, int proofId, Class<T> type) {
        return proofRepository.findByStudent_IdAndId(studentId, proofId, type);
    }

    //Получение статуса рассмотрения подтверждения по его id
    public <T> T getStatusRequest(int statusId, Class<T> type) {
        return statusProofRepository.findById(statusId, type);
    }

    //Получение подтверждения для определенного студента и достижения, которое не имеет статус "Отклонено"
    public <T> T getProofByStudentAndAchieve(int achieveId, int studentId, int statusId, Class<T> type) {
        return proofRepository.findByAchievement_IdAndStudent_IdAndStatusRequest_IdNot(achieveId, studentId, statusId, type);
    }



    //Получение списка файлов для определенного листа файлов
    public <T> List <T> getFilesForListFile(int listFileId, Class<T> type) {
        return fileRepository.findByListFile_Id(listFileId, type);
    }

    //Получение списка подтверждений с определенным статусом рассмотрения
    public <T> List <T> getProofByStatus(int statusId, Class<T> type) {
        return proofRepository.findByStatusRequest_IdOrderByDateProofDesc(statusId, type);
    }

    //Получение списка подтверждений определенного студента
    public <T> List <T> getProofsForStudent(int studentId, Class<T> type) {
        return proofRepository.findByStudent_Id(studentId, type);
    }

    //Получение списка все подтверждений для модератора
    public <T> List <T> getAllProofsForModer(Class<T> type) {
        return proofRepository.findByOrderByDateProofDesc(type);
    }
}
