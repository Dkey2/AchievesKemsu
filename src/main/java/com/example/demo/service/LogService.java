package com.example.demo.service;

import com.example.demo.entity.Log;
import com.example.demo.entity.Operation;
import com.example.demo.entity.User;
import com.example.demo.repository.LogRepository;
import com.example.demo.repository.OperationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OperationRepository operationRepository;

    //Создаем новый лог
    public void createNewLog(int creatorId, int operationId, int record_id, String oldData, String newData) {
        Log log = new Log();
        log.setChangeDateLog(LocalDate.now());
        log.setUserLog(userRepository.findById(creatorId, User.class));
        log.setOperationLog(operationRepository.findById(operationId, Operation.class));
        log.setRecordIdLog(record_id);
        log.setNewData(newData);
        log.setOldData(oldData);
        logRepository.save(log);
    }


    //Получаем лог по его id
    public <T> T getLog (int logId, Class<T> type) {
        return logRepository.findById(logId, type);
    }

    //Получаем тип операции по его id
    public <T> T getOperation (int operationId, Class<T> type) {
        return operationRepository.findById(operationId, type);
    }


    //Получаем список всех логов
    public <T> List <T> getAllLogs (Class<T> type) {
        return logRepository.findByOrderByChangeDateDesc(type);
    }

    //Получаем список логов созданных пользователями определенной роли
    public <T> List <T> getLogsByRole (int roleId, Class<T> type) {
        return logRepository.findByUser_Role_IdOrderByChangeDateDesc(roleId, type);
    }

    //Получаем список логов для определенного типа оперции
    public <T> List <T> getLogsByOperation (int operationId, Class<T> type) {
        return logRepository.findByOperation_IdOrderByChangeDateDesc(operationId, type);
    }

    //Получаем список всех типов операций
    public <T> List <T> getAllTypeOperation (Class<T> type) {
        return operationRepository.findByOrderByIdAsc(type);
    }

    //Получаем список логов для модератора (только изменение фамилии/имени)
    public <T> List <T> getLogsForModerator (Class<T> type) {
        return logRepository.findByUser_Role_IdAndOperation_IdOrderByChangeDateDesc(1, 16, type);
    }

    //Получаем список логов для администратора
    public <T> List <T> getLogsByRoleAndOperation (int roleId, int operationId, Class<T> type) {
        return logRepository.findByUser_Role_IdAndOperation_IdOrderByChangeDateDesc(roleId, operationId, type);
    }
}
