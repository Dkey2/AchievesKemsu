package com.example.demo.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel (description = "Ответ, отправляемый при успешной авторизации пользователя")
public class AuthResponse {

    @ApiModelProperty (value = "Токен доступа. Not null")
    private String accessToken;

    @ApiModelProperty (value = "Рефреш токен. Not null")
    private String refreshToken;
}
