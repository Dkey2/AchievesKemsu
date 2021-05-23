package com.example.demo.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel (description = "Ошибка при авторизации")
public class ErrorResponse {

    @ApiModelProperty (value = "Время получения ошибки")
    String timestamp;

    @ApiModelProperty (value = "Код ошибки", example = "403")
    int status;

    @ApiModelProperty (value = "Название ошибки", example = "Forbidden")
    String error;

    @ApiModelProperty (value = "Опиание ошибки", example = "Ваш аккаунт был удален")
    String message;

    @ApiModelProperty (value = "URI, к которому обращался пользователь", example = "/auth")
    String path;
}
