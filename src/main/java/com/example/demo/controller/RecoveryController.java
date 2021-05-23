package com.example.demo.controller;

import com.example.demo.entity.CodeReset;
import com.example.demo.entity.User;
import com.example.demo.service.CodeService;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Api(description = "Контроллер восстановлений/подтверждений аккаунта")
@RestController
public class RecoveryController {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private CodeService codeService;

    @ApiOperation("Отправка почты для восстановления аккаунта/пароля")
    @PostMapping("/resetPassword/{email}/{whatRecovering}")
    public ResponseEntity resetPassword(@PathVariable
                                            @ApiParam (value = "Электронная почта, на которую был зарегестрирован аккаунт. Not null", example = "patyphonanik@yandex.ru")
                                                    String email,
                                        @PathVariable
                                            @ApiParam (value = "Что мы восстанавливаем. Not null.", example = "patyphonanik@yandex.ru", allowableValues = "password, account")
                                                    String whatRecovering) {
        //Проверка на валидность введенной электронной почты
        EmailValidator emailValidator = EmailValidator.getInstance();
        boolean validEmail = emailValidator.isValid(email);
        if (!validEmail)
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email введен не корректно");

        //Проверка есть ли пользователь с такой почтой
        User user = userService.findByEmail(email);
        if (user==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с таким email не найден");

        //Проверка на наличие права восстановления аккаунта
        if (user.getStatusUser()==userService.getStatusUser("Аннигилирован")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Пользователь удален без права восстановления"); }

        //Если это восстановление аккаунта, то проверяем что аккаунт был удален
        if (whatRecovering.equals("account") && user.getStatusUser()!=userService.getStatusUser("Удален"))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Невозможно восстановить аккаунт, который не был удален");

        //Если это восстановление пароля, то проверяем что аккаунт не был удален
        if (whatRecovering.equals("password") && user.getStatusUser()==userService.getStatusUser("Удален"))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Невозможно изменить пароль от аккаунта, который был удален");

        //Проверяем, что whatRecovering принял допустимое значение
        if (!whatRecovering.equals("account") && !whatRecovering.equals("password"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверное значение переменной \"whatRecovering\" ");

        //Если все нормально, отправляем письмо
        sendMailReset(email);
        return ResponseEntity.status(HttpStatus.OK).body("На указанную почту отправлено письмо с кодом подтверждения");
    }

    @ApiOperation("Проверка кода для восстановления пароля/аккаунта")
    @PutMapping("/code/reset/{code}/{email}")
    public ResponseEntity resetCode(@PathVariable
                                        @ApiParam (value = "Код, присланный на почту пользователю. Not null. 8 символов")
                                                String code,
                                    @PathVariable
                                        @ApiParam (value = "Электронная почта, на которую зарегестрирован аккаунт. Not null.", example = "qwe123@gmail.com")
                                                String email) {
        int userId = userService.findByEmail(email).getIdUser();

        if (codeService.getCodeReset(userId).getCodeReset().equals(code))
        {
            codeService.deleteCodeReset(codeService.getCodeReset(userId));
            return ResponseEntity.status(HttpStatus.OK).body("Код подтверждения действителен");
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Код не найден");
    }

    @ApiOperation("Смена пароля для доступа к утерянному акаунту")
    @PutMapping("/code/reset/password/{password}/{email}")
    public ResponseEntity resetCodePassword(@PathVariable
                                                @ApiParam (value = "Новый пароль. Not null. Не менее 8 символов", example = "hfI86Ugw09")
                                                        String password,
                                            @PathVariable
                                                @ApiParam (value = "Электронная почта, на которую зарегестрирован аккаунт. Not null.", example = "qwe123@gmail.com")
                                                        String email) {
        User user = userService.findByEmail(email);
        if (user.getPasswordUser().equals(password))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Новый пароль не может совпадать с предыдущим");
        if (password.length()<8)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пароль должен состоять не менее чем из 8 символов");
        user.setPasswordUser(password);
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("Пароль успешно изменен");
    }

    @ApiOperation("Восстановление доступа к удаленному аккаунту")
    @PutMapping("/code/reset/account/{email}")
    public ResponseEntity resetCodeAccount(@PathVariable
                                               @ApiParam (value = "Электронная почта, на которую зарегестрирован аккаунт. Not null.", example = "qwe123@gmail.com")
                                                       String email) {
        User user = userService.findByEmail(email);
        if (user.getStatusUser()==userService.getStatusUser("Удален"))
            user.setStatusUser(userService.getStatusUser("Активен"));
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Аккаунт имеет статус - "+user.getStatusUser().getStatusUser()+". Аккаунт не может быть восстановлен");
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("Аккаунт успешно восстановлен");
    }

    //Отправка письма с кодом на почту пользователя
    private void sendMailReset(String email)  {
        try {
            String code = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
            int userId = userService.getUserId();
            if (codeService.getCodeReset(userId)!=null)
                codeService.deleteCodeReset(codeService.getCodeReset(userId));
            CodeReset codeReset = new CodeReset();
            codeReset.setCodeReset(code);
            codeReset.setUserCodeReset(userService.getUser());
            codeService.saveCodeReset(codeReset);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Код доступа для восстановления аккаунта");
            helper.setText("Сгенерированный код доступа: "+code+" \n" +
                    "Для восстановления доступа к аккаунту скопируйте этот код, перейдите в приложение и вставьте в соответствующее поле ввода.", false);
            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
