package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel (description = "Конкретное сообщение об ошибке для админа")
public interface ErrorAdminView {

    @ApiModelProperty (value = "Id сообщения об ошибке. Not null. >0", example = "27")
    @JsonProperty("errorId")
    int getId();

    @ApiModelProperty (value = "Тема сообщения об ошибке. Not null", example = "Проблемы с загрузкой")
    String getTheme();

    @ApiModelProperty (value = "Описание сообщения об ошибке. Not null", example = "Данные загружаются через раз")
    String getDescription();

    @ApiModelProperty (value = "Статус рассмотрения сообщения об ошибке", example = "Решено")
    @Value("#{target.statusMessageError.StatusErrorName}")
    String getStatusErrorName();

    @ApiModelProperty (value = "Комментарий к сообщению об ошибке", example = "Проблема была решена")
    String getComment();


    /* Данные студента */
    StudentView getStudent();
    @ApiModel(description = "Данные студента")
    interface StudentView {
        @ApiModelProperty (value = "Id студента. Not null. >0", example = "12")
        @JsonProperty("studentId")
        int getId();
    }

    /* Данные пользователя */
    @Value("#{target.studentError.userForStudent}")
    UserView getUser();
    @ApiModel(description = "Данные пользователя")
    interface UserView {

        @ApiModelProperty (value = "Id пользователя-отправителя. Not null. >0", example = "12")
        @JsonProperty("userId")
        int getId();

        @ApiModelProperty (value = "Имя пользователя-отправителя. Not null", example = "Алена")
        String getFirstName();

        @ApiModelProperty (value = "Фамилия пользователя-отправителя. Not null. >0", example = "Гордиенко")
        String getLastName();
    }
}
