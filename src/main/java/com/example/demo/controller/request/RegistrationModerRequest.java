package com.example.demo.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(description = "Запрос с данными для регистрации нового пользователя в роли модератора")
public class RegistrationModerRequest {

    @ApiModelProperty(value = "Электронная почта пользователя. Not null", example = "typ89@yandex.ru")
    private String email;

    @ApiModelProperty(value = "Пароль пользователя. Not null. Не менее 8 символов", example = "fd98TbkK13")
    private String password;

    @ApiModelProperty(value = "Имя пользователя. Not null. Только кириллица. Не менее 2 символов", example = "Игорь")
    private String firstName;

    @ApiModelProperty(value = "Фамилия пользователя. Not null. Только кириллица. Не менее 2 символов", example = "Деев")
    private String lastName;

    @ApiModelProperty(value = "Id листа институтов. Not null. >0", example = "3")
    private int listInstituteId;
}
