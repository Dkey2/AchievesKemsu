package com.example.demo.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
@AllArgsConstructor
@ApiModel (description = "Запрос с данными для заявки на создание нового достижения в системе")
public class CreationAchieveRequest {

    @ApiModelProperty (value = "Название достижения. Not null", example = "Оратор")
    private String achieveName;

    @ApiModelProperty (value = "Описание достижения. Not null", example = "Принять участие в научной конференции")
    private String achieveDescription;

    @ApiModelProperty (value = "Баллы, начисляемые за выполнение достижения. Not null", example = "15")
    private int achieveScore;

    @ApiModelProperty (value = "Дата, начиная с которой можно получить достижение. Not null", example = "2021-01-01")
    private LocalDate achieveStartDate;

    @ApiModelProperty (value = "Дата, до которой можно получить достижение. Может быть null", example = "2021-01-26")
    private Optional<LocalDate> achieveEndDate;

    @ApiModelProperty (value = "Id категории достижения. Not null. [1,5]", example = "3")
    private int categoryId;

    @ApiModelProperty (value = "Изображение достижения в виде byte[]. Not null")
    private byte[] photo;

    @ApiModelProperty (value = "Формат изображения достижения. Not null", example = "png")
    private String format;

    @ApiModelProperty (value = "Id награды за выполнение достижения. Not null [1,?]", example = "4")
    private int rewardId;
}
