package com.example.demo.service;

import com.example.demo.config.CustomUserDetails;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StreamRepository streamRepository;
    @Autowired
    private InstituteRepository instituteRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private FormEducationRepository formEducationRepository;
    @Autowired
    private StudentRepository studentRepository;

    //Получаем айди текущего авторизованного студента из токена
    public int getStudentId() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = customUserDetails.getId();
        return studentRepository.findByUser_Id(userId).getIdStudent();
    }

    //Статусы, при которых студент не отображается в рейтингах/списках для студента
    private List<String> getStatus() {
        List<String> status = new ArrayList<>();
        status.add("Удален");
        status.add("Аннигилирован");
        status.add("Забанен");
        return status;
    }

    //Создаем нового студента
    public ResponseEntity createNewStudent(int groupId, int formEducationId, int streamId, int instituteId, Student student) {
        student.setScoreStudent(0);

        FormOfEducation formOfEducation = formEducationRepository.findById(formEducationId);
        if (formOfEducation==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Форма обучения с указанным id не найдена");
        student.setFormOfEducationStudent(formOfEducation);

        Institute institute = instituteRepository.findById(instituteId);
        if (institute==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Институт с указанным id не найден");
        student.setInstituteStudent(institute);

        Stream stream = streamRepository.findById(streamId);
        if (stream==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Направление с указанным id не найдено или принадлежит другому институту");
        student.setStreamStudent(stream);

        Group group = groupRepository.findById(groupId);
        if (group==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Группа с указанным id не найдена или принадлежит другому направлению");
        student.setGroupStudent(group);

        studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.OK).body("Регистрация прошла успешно");
    }

    //Топ 10 рейтинга студентов
    public <T> List <T> getTop10Students(Optional<String> filterName, Optional<Integer> idForFilter, Class<T> type) {
        if(filterName.isEmpty())
            return studentRepository.findFirst10ByUser_StatusUser_StatusUserNotInOrderByScoreDesc(getStatus(), type);
        if (filterName.get().equals("Группа"))
            return studentRepository.findFirst10ByGroup_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc(idForFilter.get(), getStatus(), type);
        if (filterName.get().equals("Направление"))
            return studentRepository.findFirst10ByStream_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc(idForFilter.get(), getStatus(), type);
        if (filterName.get().equals("Форма"))
            return studentRepository.findFirst10ByFormOfEducation_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc(idForFilter.get(), getStatus(), type);
        if (filterName.get().equals("Институт"))
            return studentRepository.findFirst10ByInstitute_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc(idForFilter.get(), getStatus(), type);
        return null;
    }

    //Топ 50 рейтинга студентов
    public <T> List <T> getTop50Students(Optional<String> filter, Optional<Integer> id, Class<T> type) {
        if(filter.isEmpty())
            return studentRepository.findFirst50ByUser_StatusUser_StatusUserNotInOrderByScoreDesc(getStatus(), type);

        if (filter.get().equals("Группа"))
            return studentRepository.findFirst50ByGroup_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc(id.get(), getStatus(), type);
        if (filter.get().equals("Направление"))
            return studentRepository.findFirst50ByStream_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc(id.get(), getStatus(), type);
        if (filter.get().equals("Форма"))
            return studentRepository.findFirst50ByFormOfEducation_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc(id.get(), getStatus(), type);
        if (filter.get().equals("Институт"))
            return studentRepository.findFirst50ByInstitute_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc(id.get(), getStatus(), type);
        return null;
    }

    //Топ 100 рейтинга студентов
    public <T> List <T> getTop100Students(Optional<String> filter, Optional<Integer> id, Class<T> type) {
        if(filter.isEmpty())
            return studentRepository.findFirst100ByUser_StatusUser_StatusUserNotInOrderByScoreDesc(getStatus(), type);
        if (filter.get().equals("Группа"))
            return studentRepository.findFirst100ByGroup_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc(id.get(), getStatus(), type);
        if (filter.get().equals("Направление"))
            return studentRepository.findFirst100ByStream_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc(id.get(), getStatus(), type);
        if (filter.get().equals("Форма"))
            return studentRepository.findFirst100ByFormOfEducation_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc(id.get(), getStatus(), type);
        if (filter.get().equals("Институт"))
            return studentRepository.findFirst100ByInstitute_IdAndUser_StatusUser_StatusUserNotInOrderByScoreDesc(id.get(), getStatus(), type);
        return null;
    }

    //Получаем студента по его id
    public <T> T getStudentById(int studentId, Class<T> type) {
        return studentRepository.findById(studentId, type);
    }

    //Сохраняем студента
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    //Ищем студентов по фамилии или ее части для студента
    public <T> List <T> getStudentBySubstring(String substring, Class<T> type) {
        return studentRepository.findByUser_LastNameContainingIgnoreCaseAndUser_StatusUser_StatusUserNotIn(substring, getStatus(), type);
    }

    //Получаем список всех студентов для админа
    public <T> List <T> getAllStudentsForAdmin(Class<T> type) {
        return studentRepository.findByOrderByUser_LastNameAsc(type);
    }

    //Ищем студентов по фамилии или ее части для админа
    public <T> List <T> getStudentsByLastNameForAdmin(String substring, Class<T> type) {
        return studentRepository.findByUser_LastNameContainingIgnoreCase(substring, type);
    }

    //Получаем список студентов по id их статуса для админа
    public <T> List <T> getStudentsByStatusUserIdForAdmin(int statusUserId, Class<T> type) {
        return studentRepository.findByUser_StatusUser_IdOrderByUser_LastNameAsc(statusUserId, type);
    }

    //Ищем студентов по фамилии или ее части и по id их статуса для админа
    public <T> List <T> getStudentsByLastNameAndStatusUserIdForAdmin(String substring, int status, Class<T> type) {
        return studentRepository.findByUser_LastNameContainingIgnoreCaseAndUser_StatusUser_IdOrderByUser_LastNameAsc(substring, status, type);
    }

}
