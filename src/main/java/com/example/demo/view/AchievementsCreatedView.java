package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel (description = "Список всех заявок на создание достижения, отправленных авторизованным студентом")
public interface AchievementsCreatedView {

    /*  Данные достижения  */
    @ApiModelProperty(value = "id достижения, Not null", example = "4")
    @JsonProperty("achieveId")
    int getId();

    @ApiModelProperty (value = "Название достижения", example = "Домовенок Кузя")
    @JsonProperty("achieveName")
    String getName();

    @ApiModelProperty (value = "Статус активности достижения", example = "Устарело")
    @Value("#{target.statusActiveOfAchieve.statusActive}")
    @JsonProperty("statusActive")
    String getStatus();

    @ApiModelProperty (value = "Изображение достижения в виде byte[]. Not null")
    @Value("#{target.fileAchieve.dataFile}")
    byte[] getData();

    @ApiModelProperty (value = "Формат изображения достижения", example = "21")
    @Value("#{target.fileAchieve.formatFile}")
    String getFormat();
}
