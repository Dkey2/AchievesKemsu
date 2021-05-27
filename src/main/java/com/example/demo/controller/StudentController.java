package com.example.demo.controller;

import com.example.demo.controller.request.PhotoRequest;
import com.example.demo.entity.*;
import com.example.demo.view.*;
import com.example.demo.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(description = "Контроллер студента")
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private FileService fileService;
    @Autowired
    private EducationService educationService;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;


    //////////////////////////////////////////////////////////////////////////////////
    /*                               Студент                                        */
    //////////////////////////////////////////////////////////////////////////////////

    @ApiOperation("Просмотр данных профиля - для студента")
    @GetMapping("/student/getStudent")
    public StudentView getStudentById(@RequestParam
                                          @ApiParam (value = "Id студента, чей профиль мы просматриваем. Не передается, если студент смотрит свой профиль. Если чужой профиль - Not null. >0", example = "12")
                                                  Optional<Integer> studentId) {
        if (studentId.isEmpty())
            studentId = Optional.of(studentService.getStudentId());
        StudentView student = studentService.getStudentById(studentId.get(), StudentView.class);
        if(student!=null)
            return student;
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент не найден");
    }

    @ApiOperation("Просмотр данных об образовании - для студента")
    @GetMapping("/student/getStudy")
    public StudyView getStudentStudy(@RequestParam
                                         @ApiParam (value = "Id студента, чей профиль мы просматриваем. Не передается, если студент смотрит свой профиль. Если чужой профиль - Not null. >0", example = "12")
                                                 Optional<Integer> studentId) {
        if (studentId.isEmpty())
            studentId = Optional.of(studentService.getStudentId());
        StudyView student = studentService.getStudentById(studentId.get(), StudyView.class);
        if(student!=null)
            return student;
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент не найден");
    }

    @ApiOperation("Топ 10 студентов с фильтами - для студента")
    @GetMapping("/student/students10")
    public List<StudentsView> getRating10(@RequestParam
                                              @ApiParam(value = "Фамилия(или ее часть) студента, чей профиль мы ищем", example = "аст")
                                                      Optional<String> substring,
                                          @RequestParam
                                              @ApiParam(value = "Название фильтра. Передается в паре с id", example = "Институт", allowableValues = "Форма,Институт,Направление,Группа")
                                                      Optional<String> filterName,
                                          @RequestParam
                                              @ApiParam(value = "id группы, направления или др. (зависит от фильтра). Передается в паре с фильтром", example = "3")
                                                      Optional<Integer> filterId) {
        if(substring.isEmpty())
            return studentService.getTop10Students(filterName, filterId, StudentsView.class);
        else
            return studentService.getStudentBySubstring(substring.get(), StudentsView.class);
    }

    @ApiOperation("Топ 50 студентов с фильтами - для студента")
    @GetMapping("/student/students50")
    public List<StudentsView> getRating50(@RequestParam
                                              @ApiParam(value = "Фамилия(или ее часть) студента, чей профиль мы ищем", example = "аст")
                                                      Optional<String> substring,
                                          @RequestParam
                                              @ApiParam(value = "Название фильтра. Передается в паре с id", example = "Направление", allowableValues = "Форма,Институт,Направление,Группа")
                                                      Optional<String> filterName,
                                          @RequestParam
                                              @ApiParam(value = "id группы, направления или др. (зависит от фильтра). Передается в паре с фильтром", example = "3")
                                                      Optional<Integer> filterId) {
        if(substring.isEmpty())
            return studentService.getTop50Students(filterName, filterId, StudentsView.class);
        else
            return studentService.getStudentBySubstring(substring.get(), StudentsView.class);
    }

    @ApiOperation("Топ 100 студентов с фильтами - для студента")
    @GetMapping("/student/students100")
    public List<StudentsView> getRating100(@RequestParam
                                               @ApiParam(value = "Фамилия(или ее часть) студента, чей профиль мы ищем", example = "аст")
                                                       Optional<String> substring,
                                           @RequestParam
                                               @ApiParam(value = "Название фильтра. Передается в паре с id", example = "Направление", allowableValues = "Форма,Институт,Направление,Группа")
                                                       Optional<String> filterName,
                                           @RequestParam
                                               @ApiParam(value = "id группы, направления или др. (зависит от фильтра). Передается в паре с фильтром", example = "3")
                                                       Optional<Integer> filterId) {
        if(substring.isEmpty())
            return studentService.getTop100Students(filterName, filterId, StudentsView.class);
        else
            return studentService.getStudentBySubstring(substring.get(), StudentsView.class);
    }

    @ApiOperation("Изменение данных об образовании - для студента")
    @PutMapping("/student/changeData")
    public ResponseEntity changeData(@RequestParam
                                         @ApiParam(value = "Id института. Может не передаваться, тогда эти данные не будут изменены. Если передаются - Not nul. [1,15]", example = "3")
                                                 Optional<Integer> instituteId,
                                     @RequestParam
                                        @ApiParam(value = "Id направления. Может не передаваться, тогда эти данные не будут изменены. Если передаются - Not nul. >0", example = "9")
                                                 Optional<Integer> streamId,
                                     @RequestParam
                                         @ApiParam(value = "Id группы. Может не передаваться, тогда эти данные не будут изменены. Если передаются - Not nul. >0", example = "2")
                                                 Optional<Integer> groupId,
                                     @RequestParam
                                         @ApiParam(value = "Id формы обучения. Может не передаваться, тогда эти данные не будут изменены. Если передаются - Not nul. >0", example = "9")
                                                 Optional<Integer> formEducationId) {
        int studentId = studentService.getStudentId();
        Student student = studentService.getStudentById(studentId, Student.class);

        streamId.ifPresent(integer -> student.setStreamStudent(educationService.getStream(integer)));
        groupId.ifPresent(integer -> student.setGroupStudent(educationService.getGroup(integer)));
        instituteId.ifPresent(integer -> student.setInstituteStudent(educationService.getInstitute(integer)));
        formEducationId.ifPresent(integer -> student.setFormOfEducationStudent(educationService.getFormEducation(integer)));

        studentService.saveStudent(student);
        return ResponseEntity.status(HttpStatus.OK).body("Данные успешно изменены");
    }

    @ApiOperation("Удаление аккаунта студентом")
    @PutMapping("/student/deleteAccount")
    public ResponseEntity deleteAccount() {
        User user = userService.getUser();
        user.setStatusUser(userService.getStatusUser("Удален"));
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("Аккаунт успешно удален. Вы всегда можете восстановить аккаунт через указанную при регистрации почту");
    }

    @ApiOperation("Изменение фотографии профиля")
    @PutMapping("/student/changePhoto")
    public ResponseEntity changePhoto(@RequestBody
                                          @ApiParam(value = "Запрос с данными для изменения фотографии профиля")
                                                  PhotoRequest request) {
        //Получаем авторизованного студента
        int studentId = studentService.getStudentId();
        Student student = studentService.getStudentById(studentId, Student.class);

        //Проверяем, что полученный файл не пуст
        if (request.getData().length==0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Фото пустое");

        //Проверяем стандартное фото(23 id) стоит или нет.
        //Если было фото по умолчанию, то создаем новый файл
        if (student.getFileStudent().getIdFile()==23) {
            File file = new File();
            file.setFormatFile(request.getFormat());
            file.setDataFile(request.getData());
            file.setListFile(null);
            fileService.saveFile(file);
            student.setFileStudent(file);
            studentService.saveStudent(student);
            return ResponseEntity.status(HttpStatus.OK).body("Фотограция профиля успешно изменена");
        }
        //Если фото совпадает с предыдущим, выводим соответствующее сообщение
        if (Arrays.equals(student.getFileStudent().getDataFile(), request.getData()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Новое фото не может совпадать с предыдущим");

        //Если нестрандартное изображение, то меняем уже существующий файл
        return fileService.changePhoto(student, request.getData(), request.getFormat());
    }

    //////////////////////////////////////////////////////////////////////////////////
    /*                                   Админ                                      */
    //////////////////////////////////////////////////////////////////////////////////

    @ApiOperation("Список студентов - для админа/модера")
    @GetMapping("/upr/students")
    public List<StudentsAdminView> getAllForAdmin(@RequestParam
                                                      @ApiParam(value = "Подстрока с фамилией (или ее частью) студента. Фильтр, может не передаваться. Если передается - Not null", example = "Астахо")
                                                              Optional<String> substring,
                                                  @RequestParam
                                                    @ApiParam(value = "Id статуса пользователя. Not null. [1,4]", example = "1")
                                                          Optional<Integer> statusUserId) {
        if (statusUserId.isEmpty()) {
            if(substring.isEmpty())
                return studentService.getAllStudentsForAdmin(StudentsAdminView.class);
            else
                return studentService.getStudentsByLastNameForAdmin(substring.get(), StudentsAdminView.class);
        }
        else {
            if(substring.isEmpty())
                return studentService.getStudentsByStatusUserIdForAdmin(statusUserId.get(), StudentsAdminView.class);
            else
                return studentService.getStudentsByLastNameAndStatusUserIdForAdmin(substring.get(), statusUserId.get(), StudentsAdminView.class);
        }
    }

    @ApiOperation("Конкретный студент - для админа/модера")
    @GetMapping("/upr/students/{studentId}")
    public StudentAdminView getByIdForAdmin(@PathVariable
                                                @ApiParam(value = "Id студента", example = "10")
                                                        Integer studentId) {
        StudentAdminView student = studentService.getStudentById(studentId, StudentAdminView.class);
        if(student!=null)
            return student;
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Студент не найден");
    }

    @ApiOperation("Удаление студента без права восстановления - для модератора")
    @PutMapping("/moderator/deleteStudent/{studentId}")
    public ResponseEntity deleteStudentByIdForModer(@PathVariable
                                                        @ApiParam(value = "Id студента. Not null. >0", example = "18")
                                                                int studentId) {
        Student student = studentService.getStudentById(studentId, Student.class);
        if (student==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Студент не найден");

        User user = userService.getUserById(student.getUserForStudent().getIdUser(), User.class);
        //Если пользователь найден, то меняем его статус
        if(user!=null) {
            String oldData = user.getStatusUser().getStatusUser();
            user.setStatusUser(userService.getStatusUser("Аннигилирован"));
            userService.saveUser(user);
            logService.createNewLog(userService.getUserId(), 15, user.getIdUser(), oldData, user.getStatusUser().getStatusUser());
            return ResponseEntity.status(HttpStatus.OK).body("Студент удален без права на восстановление");
        }
        //Иначе выводим соответствующее сообщение
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
    }

    @ApiOperation("Изменение фамилии и имени студента - для модератора")
    @PutMapping("/moderator/changeNamesOfStudent/{studentId}")
    public ResponseEntity changeNamesOfStudent(@PathVariable
                                                   @ApiParam(value = "id студента. Not null. >0", example = "18")
                                                           int studentId,
                                               @RequestParam
                                               @ApiParam (value = "Фамилия пользователя. Может не передаваться - тогда эти данные не будут изменены. Если передается - Not null. Минимум 2 символа", example = "Берилин")
                                                       Optional<String> lastName,
                                               @RequestParam
                                                   @ApiParam (value = "Имя пользователя. Может не передаваться - тогда эти данные не будут изменены. Если передается - Not null. Минимум 2 символа", example = "Анатолий")
                                                           Optional<String> firstName) {
        Student student = studentService.getStudentById(studentId, Student.class);
        if (student==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Студент не найден");

        User user = userService.getUserById(student.getUserForStudent().getIdUser(), User.class);

        //Если пользователь найден, то меняем данные
        if(user!=null) {
            //Паттерн для проверки на валидность введенных фамилии и имени (только кириллица)
            Pattern pattern = Pattern.compile("[а-яА-Я]{2,}", Pattern.UNICODE_CHARACTER_CLASS);
            String oldData = user.getFirstNameUser()+" "+user.getLastNameUser();

            //Если фамилия передана и она корректна, меняем фамилию
            if(lastName.isPresent() && !user.getLastNameUser().equals(lastName.get()))
            {
                Matcher matcherLastName = pattern.matcher(lastName.get());
                if (!matcherLastName.matches())
                    return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Для фамилии и имени разрешено использовать только русские буквы. Минимальная длина составляет 2 символа");
                user.setLastNameUser(Character.toUpperCase(lastName.get().charAt(0))+lastName.get().substring(1).toLowerCase());
            }

            //Если имя передано и оно корректно, меняем имя
            if(firstName.isPresent() && !user.getFirstNameUser().equals(firstName.get()))
            {
                Matcher matcherFirstName = pattern.matcher(firstName.get());
                if (!matcherFirstName.matches())
                    return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Для фамилии и имени разрешено использовать только русские буквы. Минимальная длина составляет 2 символа");
                user.setFirstNameUser(Character.toUpperCase(firstName.get().charAt(0))+firstName.get().substring(1).toLowerCase());
            }

            //Сохраняем данные
            userService.saveUser(user);
            logService.createNewLog(userService.getUserId(), 16, user.getIdUser(), oldData, user.getFirstNameUser()+" "+user.getLastNameUser());
            return ResponseEntity.status(HttpStatus.OK).body("Данные успешно изменены");
        }
        //Иначе выводим соответствующее сообщение
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
    }


}
