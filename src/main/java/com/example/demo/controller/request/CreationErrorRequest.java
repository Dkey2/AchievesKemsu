package com.example.demo.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel (description = "Данные, необходимые для создания сообщения об ошибке")
public class CreationErrorRequest {

    @ApiModelProperty (value = "Тема сообщения об ошибке. Not null", example = "Редактирование данных")
    String theme;

    @ApiModelProperty (value = "Описание ошибки. Not null", example = "Не могу изменить пароль в своем профиле")
    String description;
}
