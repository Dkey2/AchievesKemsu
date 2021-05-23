package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel(description = "Список пользователей для админа")
public interface UsersView {
    @ApiModelProperty (allowableValues = "range[1,infinity]", value = "Id пользователя. Not null, >0", example = "18")
    @JsonProperty("userId")
    int getId();

    @ApiModelProperty (value = "Имя пользователя. Not null", example = "Марина")
    String getFirstName();

    @ApiModelProperty (value = "Фамилия пользователя. Not null", example = "Антонова")
    String getLastName();

    @ApiModelProperty (value = "Роль пользователя. Not null", example = "Студент")
    @Value("#{target.roleUser.nameRole}")
    @JsonProperty("roleName")
    String getName();
}
