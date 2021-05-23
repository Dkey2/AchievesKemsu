package com.example.demo.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel (description = "Получение нового токена, если старый устарел")
public class NewTokenResponse {
    @ApiModelProperty (value = "Новый токен доступа. Not null")
    private String accessToken;
}
