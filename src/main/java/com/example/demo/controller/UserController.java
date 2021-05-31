package com.example.demo.controller;

import com.example.demo.controller.request.CreationBanRequest;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import com.example.demo.view.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(description = "Контроллер пользователя")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private BanService banService;
    @Autowired
    private EducationService educationService;
    @Autowired
    private LogService logService;

    @ApiOperation("Изменение имени и фамилии.")
    @PutMapping("/change/name")
    public ResponseEntity changeName(@RequestParam
                                         @ApiParam (value = "Фамилия пользователя. Может не передаваться - тогда эти данные не будут изменены. Если передается - Not null. Минимум 2 символа", example = "Берилин")
                                                 Optional<String> lastName,
                                     @RequestParam
                                        @ApiParam (value = "Имя пользователя. Может не передаваться - тогда эти данные не будут изменены. Если передается - Not null. Минимум 2 символа", example = "Анатолий")
                                                Optional<String> firstName) {
        //Паттерн для проверки на валидность введенных фамилии и имени (только кириллица)
        Pattern pattern = Pattern.compile("[а-яА-Я]{2,}", Pattern.UNICODE_CHARACTER_CLASS);

        //Получаем текущего пользователя
        User user = userService.findUser(userService.getUserId());
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
        if (user.getRoleUser().getIdRole()==1)
            logService.createNewLog(userService.getUserId(), 16, user.getIdUser(), oldData, "studentId="+studentService.getStudentId()+" - "+user.getFirstNameUser()+" "+user.getLastNameUser());
        else
            logService.createNewLog(userService.getUserId(), 16, user.getIdUser(), oldData, user.getFirstNameUser()+" "+user.getLastNameUser());
        return ResponseEntity.status(HttpStatus.OK).body("Данные успешно изменены");
    }

    @ApiOperation("Изменение email")
    @PutMapping("/change/email/{email}")
    public ResponseEntity changeEmail(@PathVariable
                                          @ApiParam(value = "Новая электронная почта. Not null", example = "patyphonanik@yandex.ru")
                                                  String email) {
        //Получаем текущего пользователя
        User user = userService.findUser(userService.getUserId());

        //Проверка на совпадение почты с текущей
        if (user.getEmailUser().equals(email))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Новый email не может совпадать с предыдущим");

        //Проверка на наличие зарегистрированного пользователя с указанной электронной почтой
        User test = userService.findByEmail(email);
        if (test != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь с таким email уже существует!");

        //Проверка на валидность введенной электронной почты
        EmailValidator emailValidator = EmailValidator.getInstance();
        boolean validEmail = emailValidator.isValid(email);
        if (!validEmail)
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email введен не корректно");

        //Если все проверки прошли, менияем данные и сохраняем
        user.setEmailUser(email);
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("Email успешно изменен");
    }

    @ApiOperation("Изменение пароля")
    @PutMapping("/change/password/{password}")
    public ResponseEntity changePassword(@PathVariable
                                             @ApiParam(value = "Новый пароль. Not null. Минимум 8 символов", example = "bWo19NINe")
                                                     String password) {
        //Получаем текущего пользователя
        int userId = userService.getUserId();
        User user = userService.findUser(userId);

        //Проверяем новый пароль на совпадение с предыдущим
        if (user.getPasswordUser().equals(password))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Новый пароль не может совпадать с предыдущим");

        //Проверяем длину пароля
        if (password.length()<8)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пароль должен состоять не менее чем из 8 символов");

        //Если все нормально, записываем новый пароль и сохраняем
        user.setPasswordUser(password);
        userService.savePassword(user);
        return ResponseEntity.status(HttpStatus.OK).body("Пароль успешно изменен");
    }

    @ApiOperation("Email авторизованного пользователя")
    @GetMapping("student/getEmail")
    public String getUserEmail() {
        return userService.findUser(userService.getUserId()).getEmailUser();
    }

    @ApiOperation("Список модераторов для админа")
    @GetMapping("admin/getModerators")
    public List <ModersView> getModerators() {
        return userService.getAllModerators(ModersView.class);
    }

    @ApiOperation("Назначение листа институтов модератору для админа")
    @PutMapping("admin/changeListForModerator/{moderatorId}/{listInstituteId}")
    public ResponseEntity changeListForModerator(@PathVariable int moderatorId, @PathVariable int listInstituteId) {
        //Проверяем есть ли модератор с таким id
        Moderator moderator = userService.getModerator(moderatorId, Moderator.class);
        if (moderator==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Модератор с указанным id не найден");
        //Проверяем есть ли лист институтов с таким id
        ListInstitute listInstitute = educationService.getListInstitute(listInstituteId);
        if (listInstitute==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Лист институтов с указанным id не найден");

        moderator.setListInstituteForModerator(listInstitute);
        userService.saveModer(moderator);

        return ResponseEntity.status(HttpStatus.OK).body("Модератору успешно изменен лист институтов");
    }

    @ApiOperation("Список пользователей (по роли или всех) - для админа")
    @GetMapping("admin/getUsers")
    public List <UsersView> getUsers(@RequestParam
                                         @ApiParam(value = "Id роли пользователя. Фильтр, может не передаваться. Если передается - Not null. [1,3]", example = "1")
                                                 Optional<Integer> roleId,
                                     @RequestParam
                                     @ApiParam(value = "Id статуса пользователя. Фильтр, может не передаваться. Если передается - Not null. [1,4]", example = "3")
                                             Optional<Integer> statusId,
                                     @RequestParam
                                     @ApiParam(value = "Подстрока с фамилией(или ее частью) пользователя, которого ищем. Фильтр, может не передаваться. Если передается - Not null", example = "КИсл")
                                             Optional<String > substring) {
        if (roleId.isEmpty()) {
            if (substring.isEmpty()) {
                if (statusId.isEmpty())
                    return userService.getAllUser(UsersView.class);
                else
                    return userService.getUserByStatus(statusId.get(), UsersView.class);
            }
            else {
                if (statusId.isEmpty())
                    return userService.getUserBySubstring(substring.get(), UsersView.class);
                else
                    return userService.getUserBySubstringAndStatus(substring.get(), statusId.get(), UsersView.class);
            }
        }
        else {
            if (substring.isEmpty())
            {
                if (statusId.isEmpty())
                    return userService.getUserByRole(roleId.get(), UsersView.class);
                else
                    return userService.getUserByRoleAndStatus(roleId.get(), statusId.get(), UsersView.class);
            }
            else
            {
                if (statusId.isEmpty())
                    return userService.getUserBySubstringAndRole(substring.get(), roleId.get(), UsersView.class);
                else
                    return userService.getUserBySubstringAndRoleAndStatus(substring.get(), roleId.get(), statusId.get(), UsersView.class);
            }
        }
    }

    @ApiOperation("Конкретный пользователь - для админа")
    @GetMapping("/admin/getUser/{userId}")
    public UserView getUserByIdForAdmin(@PathVariable
                                            @ApiParam(value = "id пользователя Not null. >0", example = "12")
                                                    int userId) {
        UserView user = userService.getUserById(userId, UserView.class);
        //Проверяем, есть ли у нас пользователь с таким id
        if(user!=null)
            return user;
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
    }

    @ApiOperation("Удаление пользователя без права восстановления - для админа")
    @PutMapping("/admin/deleteUser/{userId}")
    public ResponseEntity deleteUserByIdForAdmin(@PathVariable
                                                     @ApiParam(value = "id пользователя. Not null. >0", example = "16")
                                                             int userId) {
        User user = userService.getUserById(userId, User.class);
        //Если пользователь найден, то меняем его статус
        if(user!=null) {
            String oldData = user.getStatusUser().getStatusUser();
            user.setStatusUser(userService.getStatusUser("Аннигилирован"));
            userService.saveUser(user);
            logService.createNewLog(userService.getUserId(), 15, user.getIdUser(), oldData, user.getStatusUser().getStatusUser());
            return ResponseEntity.status(HttpStatus.OK).body("Пользователь удален без права на восстановление");
        }
        //Иначе выводим соответствующее сообщение
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
    }

    @ApiOperation("Бан студента")
    @PutMapping("/moderator/bannedUser")
    public ResponseEntity bannedUser(@RequestBody
                                         @ApiParam(value = "Запрос с данными для создания бана")
                                                 CreationBanRequest creationBanRequest) {

        //Проверяем есть ли такой студент
        Student student = studentService.getStudentById(creationBanRequest.getStudentId(), Student.class);
        if (student==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Студент не найден");

        //Проверяем смогли ли мы получить для него пользователя
        int userId = student.getUserForStudent().getIdUser();
        User user = userService.findUser(userId);
        if (user==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");

        //Если все хорошо, записываем статус и сохраняем пользователя
        String oldData = user.getStatusUser().getStatusUser();
        user.setStatusUser(userService.getStatusUser("Забанен"));
        logService.createNewLog(userService.getUserId(), 15, user.getIdUser(), oldData, user.getStatusUser().getStatusUser());
        userService.saveUser(user);

        //Создаем бан и сохраняем
        Ban ban = new Ban();
        ban.setDateEndBan(creationBanRequest.getBanDateEnd());
        ban.setCreatorBan(userService.findUser(userService.getUserId()));
        ban.setReasonBan(creationBanRequest.getBanReason());
        ban.setUserBanned(userService.findUser(userId));
        banService.saveBan(ban);
        logService.createNewLog(userService.getUserId(), 14, ban.getIdBan(), null, "userId="+user.getIdUser()+" - "+ban.getReasonBan()+" "+ban.getDateEndBan());

        return ResponseEntity.status(HttpStatus.OK).body("Студент забанен");
    }

    @ApiOperation("Список ролей для админа")
    @GetMapping ("/admin/getRoles")
    public List<RoleView> getTypeOperation() {
        return userService.getAllRoles(RoleView.class);
    }
}
