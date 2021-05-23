package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel (description = "Конкретное сообщение об ошибке для студента")
public interface ErrorView {

    @ApiModelProperty(value = "Id сообщения об ошибке. Not null. >0", example = "27")
    @JsonProperty("errorId")
    int getId();

    @ApiModelProperty (value = "Тема сообщения об ошибке. Not null", example = "Проблемы с загрузкой")
    String getTheme();

    @ApiModelProperty (value = "Описание сообщения об ошибке. Not null", example = "Данные загружаются через раз")
    String getDescription();

    @ApiModelProperty (value = "Комментарий к сообщению об ошибке", example = "Проблема была решена")
    String getComment();

    @ApiModelProperty (value = "Статус рассмотрения сообщения об ошибке", example = "Решено")
    @Value("#{target.statusMessageError.StatusErrorName}")
    String getStatusErrorName();
}
