package com.example.demo.controller;

import com.example.demo.config.jwt.JwtProvider;
import com.example.demo.controller.request.AuthRequest;
import com.example.demo.controller.request.RegistrationAdminRequest;
import com.example.demo.controller.request.RegistrationModerRequest;
import com.example.demo.controller.request.RegistrationUserRequest;
import com.example.demo.controller.response.AuthResponse;
import com.example.demo.controller.response.NewTokenResponse;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.hasText;

@Api(description = "Контроллер авторизации, регистрации")
@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private FileService fileService;
    @Autowired
    private BanService banService;
    @Autowired
    private EducationService educationService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private LogService logService;

    public static final String REFRESH = "Refresh";

    @ApiOperation("Авторизация пользователя")
    @PostMapping("/auth")
    public AuthResponse authorizeUser (@RequestBody
                                       @ApiParam(value = "Запрос с данными для авторизации пользователя")
                                               AuthRequest authRequest) {
        //Проверяем зарегистрирован ли пользователь с такой почтой и правильно ли введен пароль
        User user = userService.findByEmailAndPassword(authRequest.getEmail(), authRequest.getPassword());

        //Проверяем удален ли пользователь
        if (user.getStatusUser() == userService.getStatusUser("Удален"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Пользователь удален");

        //Проверяем удален ли пользователь без права восстановления
        if (user.getStatusUser() == userService.getStatusUser("Аннигилирован"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Пользователь удален без права восстановления");

        //Проверяем забанен ли пользователь
        if (user.getStatusUser() == userService.getStatusUser("Забанен"))
        {
            Ban ban = userService.getBan(user.getIdUser());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateNow = new Date();
            String stringDate = simpleDateFormat.format(dateNow);
            //Если дата окончания баны приходится на текущую дату, то бан удаляется, статус пользователя меняетяс на "Активен" и авторизация проходит
            if (ban.getDateEndBan().toString().equals(stringDate))
            {
                user.setStatusUser(userService.getStatusUser("Активен"));
                userService.saveUser(user);
                banService.deleteBan(ban);
            }
            //Если бан еще не кончился, выводится соответствующее сообщение
            else
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Пользователь забанен по причине: "+ban.getReasonBan() + ". Бан истечет " + ban.getDateEndBan());
        }

        //Если все проверки прошли успешно, генерируем токены и возвращаем на клиент
        String accessToken = jwtProvider.generateAccessToken(user.getEmailUser());
        String refreshToken = jwtProvider.generateRefreshToken(user.getEmailUser());
        return new AuthResponse(accessToken, refreshToken);
    }

    @ApiOperation("Новый токен доступа, если старый устарел. Для получения нового токена необходимо отправить Рефреш токен в header в виде: ключ=Refresh, значение=Refresh значениеРефрешТокена")
    @GetMapping("/newToken")
    public NewTokenResponse generateNewToken(HttpServletRequest request) {

        //Получаем пару ключ-значение, где ключ=Refresh
        String header = request.getHeader(REFRESH);
        String refreshToken;

        //Если такая пара есть, считываем рефреш токен
        if (hasText(header) && header.startsWith("Refresh "))
            refreshToken = header.substring(8);
            //Если нет, сохраняем пустое значение
        else
            refreshToken = null;

        //Если рефреш токен не пустой
        if (refreshToken!=null)
        {
            //И при этом валидный, то генерируем и отдаем новый токен доступа
            if ( jwtProvider.validateRefreshToken(refreshToken)) {
                String email = jwtProvider.getEmailFromRefreshToken(refreshToken);
                String token = jwtProvider.generateAccessToken(email);
                return new NewTokenResponse(token);
            }
            //Если рефреш токен не валидный, выводим сообщение об ошибке
            else
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Рефреш токен недействителен");
        }
        //Если рефреш токен пуст, выводим сообщение об ошибке
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Рефреш токен пуст");
    }

    @ApiOperation("Регистрация пользователя в роли студента")
    @PostMapping("/register")
    public ResponseEntity registerStudent (@RequestBody
                                               @ApiParam(value = "Запрос с данными для регистрации пользователя в роли студента")
                                                       RegistrationUserRequest registrationUserRequest) {
        //Проверяем правильность введенных данных
        validateInputData(registrationUserRequest.getEmail(), registrationUserRequest.getPassword(), registrationUserRequest.getFirstName(), registrationUserRequest.getLastName());

        User user = new User();
        //Записываем в нового пользователя введенные данные
        user.setPasswordUser(registrationUserRequest.getPassword());
        user.setEmailUser(registrationUserRequest.getEmail());
        user.setFirstNameUser(Character.toUpperCase(registrationUserRequest.getFirstName().charAt(0))+registrationUserRequest.getFirstName().substring(1).toLowerCase());
        user.setLastNameUser(Character.toUpperCase(registrationUserRequest.getLastName().charAt(0))+registrationUserRequest.getLastName().substring(1).toLowerCase());
        user = userService.createNewUserForStudent(user);

        //В качестве фото профиля берется стандратная иконка
        File file = fileService.getFileById(23, File.class);
        fileService.saveFile(file);

        //Записываем в нового студента введенные данные
        Student student = new Student();
        student.setUserForStudent(user);
        student.setFileStudent(file);
        return studentService.createNewStudent(
                registrationUserRequest.getGroupId(),
                registrationUserRequest.getFormEducationId(),
                registrationUserRequest.getStreamId(),
                registrationUserRequest.getInstituteId(),
                student);

    }

    @ApiOperation("Регистрация пользователя в роли администратора. Доступно только админу")
    @PostMapping("/admin/registerAdmin")
    public ResponseEntity registerAdmin (@RequestBody
                                           @ApiParam(value = "Запрос с данными для регистрации пользователя в роли администратора")
                                                 RegistrationAdminRequest registrationAdminRequest) {
        //Проверяем правильность введенных данных
        validateInputData(registrationAdminRequest.getEmail(), registrationAdminRequest.getPassword(), registrationAdminRequest.getFirstName(), registrationAdminRequest.getLastName());

        //Проверка на наличие зарегистрированного пользователя с указанной электронной почтой
        User test = userService.findByEmail(registrationAdminRequest.getEmail());
        if (test != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь с таким email уже существует!");

        //Записываем в нового пользователя введенные данные
        User user = new User();
        user.setPasswordUser(registrationAdminRequest.getPassword());
        user.setEmailUser(registrationAdminRequest.getEmail());
        user.setFirstNameUser(Character.toUpperCase(registrationAdminRequest.getFirstName().charAt(0))+registrationAdminRequest.getFirstName().substring(1).toLowerCase());
        user.setLastNameUser(Character.toUpperCase(registrationAdminRequest.getLastName().charAt(0))+registrationAdminRequest.getLastName().substring(1).toLowerCase());
        userService.createNewUserForAdmin(user);

        logService.createNewLog(userService.getUserId(), 6, user.getIdUser(), null, user.getFirstNameUser()+" "+user.getLastNameUser()+" - "+user.getRoleUser());

        return ResponseEntity.status(HttpStatus.OK).body("Регистрация прошла успешно");
    }

    //Принимает данные в body, необходимые для регистрации. Описание в RegistrationAdminRequest
    //Возвращает сообщение об успехе/неудаче
    @ApiOperation("Регистрация пользователя в роли администратора. Доступно только админу")
    @PostMapping("/admin/registerModer")
    public ResponseEntity registerModer (@RequestBody
                                             @ApiParam(value = "Запрос с данными для регистрации пользователя в роли модератора")
                                                     RegistrationModerRequest registrationModerRequest) {
        //Проверяем правильность введенных данных
        validateInputData(registrationModerRequest.getEmail(), registrationModerRequest.getPassword(), registrationModerRequest.getFirstName(), registrationModerRequest.getLastName());

        //Записываем в нового пользователя введенные данные
        User user = new User();
        user.setPasswordUser(registrationModerRequest.getPassword());
        user.setEmailUser(registrationModerRequest.getEmail());
        user.setFirstNameUser(Character.toUpperCase(registrationModerRequest.getFirstName().charAt(0))+registrationModerRequest.getFirstName().substring(1).toLowerCase());
        user.setLastNameUser(Character.toUpperCase(registrationModerRequest.getLastName().charAt(0))+registrationModerRequest.getLastName().substring(1).toLowerCase());
        userService.createNewUserForAdmin(user);

        //Получаем лист с доступными модератору институтами
        ListInstitute listInstitute = educationService.getListInstitute(registrationModerRequest.getListInstituteId());
        if (listInstitute==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Лист институтов с указанным id не найден");

        //Создаем нового подератора и записываем в него данные
        Moderator moderator = new Moderator();
        moderator.setUser(user);
        moderator.setListInstituteForModerator(listInstitute);
        userService.saveModer(moderator);

        logService.createNewLog(userService.getUserId(), 7, user.getIdUser(), null, user.getFirstNameUser()+" "+user.getLastNameUser()+" - "+user.getRoleUser());

        return ResponseEntity.status(HttpStatus.OK).body("Регистрация прошла успешно");
    }

    private void validateInputData(String email, String password, String firstName, String lastName) {
        //Проверка на валидность введенной электронной почты
        EmailValidator emailValidator = EmailValidator.getInstance();
        boolean validEmail = emailValidator.isValid(email);
        if (!validEmail)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email введен не корректно");

        //Проверка на валидность введенных фамилии и имени
        Pattern pattern = Pattern.compile("[а-яА-Я]{2,}", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcherFirstName = pattern.matcher(firstName);
        Matcher matcherLastName = pattern.matcher(lastName);
        if (!matcherFirstName.matches() || !matcherLastName.matches())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Для фамилии и имени разрешено использовать только русские буквы. Минимальная длина составляет 2 символа");

        //Проверка на длину пароля
        if (password.length() < 8)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пароль должен состоять не менее чем из 8 символов");

        //Проверка на наличие зарегистрированного пользователя с указанной электронной почтой
        User test = userService.findByEmail(email);
        if (test != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Пользователь с таким email уже существует!");
    }
}
