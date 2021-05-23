package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@ApiModel(description = "Конкретный пользователь для админа")
public interface UserView {
    @ApiModelProperty(allowableValues = "range[1,infinity]", value = "Id пользователя. Not null, >0", example = "18")
    @JsonProperty("userId")
    int getId();

    @ApiModelProperty (value = "Имя пользователя. Not null", example = "Марина")
    String getFirstName();

    @ApiModelProperty (value = "Фамилия пользователя. Not null", example = "Антонова")
    String getLastName();

    @ApiModelProperty (value = "Электронная почта пользователя. Not null", example = "mar_ant@mail.ru")
    String getEmail();

    @ApiModelProperty (value = "Дата регистрации пользователя. Not null", example = "2021-02-14")
    LocalDate getDateRegistration();

    @ApiModelProperty (value = "Статус пользователя. Not null", example = "Забанен")
    @Value("#{target.statusUser.statusUser}")
    @JsonProperty(value = "statusUser")
    String getStatusUser();

    @ApiModelProperty (value = "Роль пользователя. Not null", example = "Студент")
    @Value("#{target.roleUser.nameRole}")
    @JsonProperty("roleName")
    String getName();
}
