package com.example.demo.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(description = "Запрос авторизации")
public class AuthRequest {

    @ApiModelProperty (value = "Электронная почта пользователя. Not null", example = "qweqr98@gmail.com")
    private String email;

    @ApiModelProperty (value = "Пароль. Not null", example = "Hu87qP12")
    private String password;
}
