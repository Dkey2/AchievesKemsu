package com.example.demo.service;

import com.example.demo.config.CustomUserDetails;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private StatusUserRepository statusUserRepository;
    @Autowired
    private BanRepository banRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModeratorRepository moderatorRepository;

    //Добавляем нового пользователя c ролью "Студент"
    public User createNewUserForStudent(User user) {
        Role role = roleRepository.findByName("Студент");
        user.setRoleUser(role);

        StatusUser statusUser = statusUserRepository.findByStatusUser("Активен");
        user.setStatusUser(statusUser);

        user.setPasswordUser(passwordEncoder.encode(user.getPasswordUser()));

        user.setDateRegistrationUser(LocalDate.now());
        userRepository.save(user);
        return user;
    }

    //Добавляем нового пользователя. При регистрации ему присуждается роль "Админ"
    public void createNewUserForAdmin(User user) {
        //Получаем нужную роль и статус
        Role role = roleRepository.findByName("Администратор");
        StatusUser statusUser = statusUserRepository.findByStatusUser("Активен");

        //Записываем данные и сохраняем
        user.setRoleUser(role);
        user.setStatusUser(statusUser);
        user.setPasswordUser(passwordEncoder.encode(user.getPasswordUser()));
        user.setDateRegistrationUser(LocalDate.now());
        userRepository.save(user);
    }

    //Добавляем нового пользователя. При регистрации ему присуждается роль "Модератор"
    public void createNewUserForModer(User user) {
        //Получаем нужную роль и статус
        Role role = roleRepository.findByName("Модератор");
        StatusUser statusUser = statusUserRepository.findByStatusUser("Активен");

        //Записываем данные и сохраняем
        user.setRoleUser(role);
        user.setStatusUser(statusUser);
        user.setPasswordUser(passwordEncoder.encode(user.getPasswordUser()));
        user.setDateRegistrationUser(LocalDate.now());
        userRepository.save(user);
    }

    /*
    * Сначала ищем пользователя, если юзера с таким email нет, выдаем ошибку.
    * Если такой пользователь есть, сверяем введеный пароль с паролем в базе.
    * Если все норм, возвращаем данные юзера
    * Если пароль неверный, выводим соответствующую ошибку
    */
    public User findByEmailAndPassword (String email, String password) {
        User user = findByEmail(email);
        if (user != null) {
            if (passwordEncoder.matches(password,user.getPasswordUser()))
                return user;
            else
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Неверный пароль");
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с таким email не найден");
    }

    //Получаем айди текущего авторизованного пользователя из токена
    public int getUserId() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customUserDetails.getId();
    }

    //Получаем текущего авторизованного пользователя из токена
    public User getUser() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = customUserDetails.getId();
        return userRepository.findById(userId, User.class);
    }


    //Получаем бан пользователя по id пользователя
    public Ban getBan(int userId) { return banRepository.findByUserBanned_Id(userId); }

    //Получаем пользователя по его id
    public User findUser(Integer userId) { return userRepository.findById(userId, User.class); }

    //Получаем роль по ее id
    public Role getRole(int roleId) { return roleRepository.findById(roleId); }

    //Поиск пользоваетеля по email
    public User findByEmail(String email) { return userRepository.findByEmail(email); }

    //Получаем статус пользователя по названию статуса
    public StatusUser getStatusUser(String status) { return statusUserRepository.findByStatusUser(status); }



    //Сохранаяем данные пользователя в базе
    public void saveUser(User user) {
        userRepository.save(user);
    }

    //Сохранаяем данные пользователя в базе
    public void savePassword(User user) {
        user.setPasswordUser(passwordEncoder.encode(user.getPasswordUser()));
        userRepository.save(user);
    }

    //Сохранаяем данные модератора в базе
    public void saveModer(Moderator moderator) {
        moderatorRepository.save(moderator);
    }


    //Получаем пользователя по его id в обертке
    public <T> T getUserById(int userId, Class<T> type) {
        return userRepository.findById(userId, type);
    }

    public <T> T getModerator(int moderatorId, Class<T> type) {
        return moderatorRepository.findById(moderatorId, type);
    }

    public <T> T getModeratorByUserId(int userId, Class<T> type) {
        return moderatorRepository.findByUser_Id(userId, type);
    }



    //Получаем список всех пользователей
    public <T> List <T> getAllUser(Class<T> type) {
        return userRepository.findBy(type);
    }

    //Получаем список ролей
    public <T> List <T> getAllRoles(Class<T> type) {
        return roleRepository.findBy(type);
    }

    //Получаем список пользователей по id роли
    public <T> List <T> getUserByRole(int roleId, Class<T> type) {
        return userRepository.findByRole_Id(roleId, type);
    }

    //Получаем список пользователей по id статуса
    public <T> List <T> getUserByStatus(int statusId, Class<T> type) {
        return userRepository.findByStatusUser_Id(statusId, type);
    }
    public <T> List <T> getAllModerators(Class<T> type) {
        return moderatorRepository.findBy(type);
    }

    //Получаем список пользователей, чья фамилия содержит подстроку
    public <T> List <T> getUserBySubstring(String substring, Class<T> type) {
        return userRepository.findByLastNameContainingIgnoreCase(substring, type);
    }

    //Получаем список пользователей по id роли и статуса
    public <T> List <T> getUserByRoleAndStatus(int roleId, int statusId, Class<T> type) {
        return userRepository.findByRole_IdAndStatusUser_Id(roleId, statusId, type);
    }

    //Получаем список по id роли и подстроке (тех, чья фамилия содержит подстроку)
    public <T> List <T> getUserBySubstringAndRole(String substring, int roleId, Class<T> type) {
        return userRepository.findByLastNameContainingIgnoreCaseAndRole_Id(substring, roleId, type);
    }

    //Получаем список по id статуса и подстроке (тех, чья фамилия содержит подстроку)
    public <T> List <T> getUserBySubstringAndStatus(String substring, int statusId, Class<T> type) {
        return userRepository.findByLastNameContainingIgnoreCaseAndStatusUser_Id(substring, statusId, type);
    }

    //Получаем список по id роли и статуса и подстроке (тех, чья фамилия содержит подстроку)
    public <T> List <T> getUserBySubstringAndRoleAndStatus(String substring, int roleId, int statusId, Class<T> type) {
        return userRepository.findByLastNameContainingIgnoreCaseAndRole_IdAndStatusUser_Id(substring, roleId, statusId, type);
    }
}
