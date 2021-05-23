package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel (description = "Список ролей пользователя")
public interface RoleView {

    @ApiModelProperty (value = "Id роли", example = "3")
    @JsonProperty("roleId")
    int getId();

    @ApiModelProperty (value = "Название роли", example = "Модератор")
    @JsonProperty("roleName")
    String getName();
}
