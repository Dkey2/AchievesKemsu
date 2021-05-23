package com.example.demo.service;

import com.example.demo.config.CustomUserDetails;
import com.example.demo.entity.MessageError;
import com.example.demo.entity.StatusMessageError;
import com.example.demo.entity.Student;
import com.example.demo.repository.MessageErrorRepository;
import com.example.demo.repository.StatusErrorRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageErrorService {
    @Autowired
    MessageErrorRepository messageErrorRepository;
    @Autowired
    StatusErrorRepository statusErrorRepository;
    @Autowired
    StudentRepository studentRepository;

    //Создаем новое сообщение об ошибке
    public void createNewMessageError(MessageError messageError) {
        StatusMessageError statusMessageError = statusErrorRepository.findByStatusErrorName("В обработке", StatusMessageError.class);
        messageError.setStatusMessageError(statusMessageError);

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = customUserDetails.getId();
        int studentId = studentRepository.findByUser_Id(userId).getIdStudent();

        Student student = studentRepository.findById(studentId, Student.class);
        int maxId = messageErrorRepository.findFirstByOrderByIdDesc(MessageError.class).getIdError();
        messageError.setIdError(maxId+1);
        messageError.setStudentError(student);
        messageError.setCommentError(null);
        messageErrorRepository.save(messageError);
    }


    //Сохранение сообщения об ошибке
    public MessageError saveMassageError(MessageError messageError) {
        return messageErrorRepository.save(messageError);
    }


    //Получение статуса рассмотрения сообщения об ошибке по id статуса
    public <T> T getStatusMessageError(int statusId, Class<T> type) {
        return statusErrorRepository.findById(statusId, type);
    }

    //Получение конкретного сообщения об ошибке для админа
    public <T> T getMessageErrorForAdmin(int errorId, Class<T> type) {
        return messageErrorRepository.findById(errorId, type);
    }

    //Получение конкретного сообщения об ошибке для студента
    public <T> T getMessageErrorOfStudent(int studentId, int messageErrorId, Class<T> type) {
        return messageErrorRepository.findByStudent_IdAndId(studentId, messageErrorId, type);
    }

    //Получение списка всех сообщений об ошибке для админа
    public <T> List <T> getAllMessageErrorForAdmin(Class<T> type) {
        return messageErrorRepository.findBy(type);
    }

    //Получение списка сообщений об ошибке с определенным статусом рассмотрения
    public <T> List <T> getMessageErrorByStatus(int statusId, Class<T> type) {
        return messageErrorRepository.findByStatusMessageError_Id(statusId, type);
    }

    //Получение списка всех статусов рассмотрения сообщения об ошибке
    public <T> List <T> getAllStatusMessageError(Class<T> type) {
        return statusErrorRepository.findBy(type);
    }

    //Получение списка сообщений об ошибке для студента
    public <T> List <T> getAllMessageErrorOfStudent(int studentId, Class<T> type) {
        return messageErrorRepository.findByStudent_Id(studentId, type);
    }

}
