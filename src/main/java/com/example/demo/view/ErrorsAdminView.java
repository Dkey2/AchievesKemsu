package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel (description = "Список сообщений об ошибке для админа")
public interface ErrorsAdminView {

    @ApiModelProperty(value = "Id сообщения об ошибке. Not null. >0", example = "27")
    @JsonProperty("errorId")
    int getId();

    @ApiModelProperty (value = "Тема сообщения об ошибке. Not null", example = "Проблемы с загрузкой")
    String getTheme();

    @ApiModelProperty (value = "Статус рассмотрения сообщения об ошибке. Not null", example = "Решено")
    @Value("#{target.statusMessageError.StatusErrorName}")
    String getStatusErrorName();

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
