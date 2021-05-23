package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel(description = "Конкретная заявка на добавление нового достижения в систему")
public interface AchievementCreatedView {

    /*  Данные достижения  */
    @ApiModelProperty(value = "id достижения, Not null", example = "4")
    @JsonProperty("achieveId")
    int getId();

    @ApiModelProperty (value = "Название достижения", example = "Домовенок Кузя")
    @JsonProperty("achieveName")
    String getName();

    @ApiModelProperty (value = "Описание достижения", example = "Принять участие в субботнике")
    String getDescription();

    @ApiModelProperty (value = "Баллы начисляемые за выполнение достижения", example = "10")
    int getScore();

    @ApiModelProperty (value = "Файл с изображением достижения")
    FileView getFile();

    @ApiModelProperty (value = "Статус активности достижения", example = "Устарело")
    @Value("#{target.statusActiveOfAchieve.statusActive}")
    String getStatus();


    /*  Данные награды  */
    RewardView getReward();
    interface RewardView {
        @ApiModelProperty(value = "Название награды. Not null", example = "Бесплатный кофе")
        @JsonProperty("rewardName")
        String getName();

        @ApiModelProperty(value = "Файл с иконкой награды")
        FileView getFile();
    }


    /*  Данные категории  */
    CategoryView getCategory();
    interface CategoryView {
        @ApiModelProperty(value = "Название категории. Not null", example = "Общество")
        @JsonProperty("categoryName")
        String getName();

        @ApiModelProperty(value = "Файл с иконкой категории")
        FileView getFile();
    }


    /*  Данные файла с изображением  */
    @ApiModel (description = "Данные файла: изображение и формат")
    interface FileView {
        @ApiModelProperty (value = "Изображение в виде byte[]. Not null")
        byte[] getData();

        @ApiModelProperty (value = "Формат изображения. Not null", example = "jpg")
        String getFormat();
    }
}
