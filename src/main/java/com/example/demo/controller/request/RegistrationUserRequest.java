package com.example.demo.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(description = "Запрос с данными для регистрации нового пользователя в роли студента")
public class RegistrationUserRequest {

    /*  Данные пользователя  */
    @ApiModelProperty(value = "Электронная почта пользователя. Not null", example = "typ89@yandex.ru")
    private String email;

    @ApiModelProperty(value = "Пароль пользователя. Not null. Не менее 8 символов", example = "fd98TbkK13")
    private String password;

    @ApiModelProperty(value = "Имя пользователя. Not null. Только кириллица. Не менее 2 символов", example = "Игорь")
    private String firstName;

    @ApiModelProperty(value = "Фамилия пользователя. Not null. Только кириллица. Не менее 2 символов", example = "Деев")
    private String lastName;


    /*  Данные об образовании  */
    @ApiModelProperty(allowableValues = "range[1,infinity]", value = "Id направления. Not null,[1,?]", example = "1")
    private int streamId;

    @ApiModelProperty(allowableValues = "range[1,15]", value = "Id института. Not null, [1,15]", example = "7")
    private int instituteId;

    @ApiModelProperty(value = "Id группы. Not null, [1,?]", example = "5")
    private int groupId;

    @ApiModelProperty(allowableValues = "range[1,3]", value = "Id формы обучения. Not null, [1,3]", example = "1")
    private int formEducationId;
}
