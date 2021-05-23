package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel (description = "Конкретное достижение в системе")
public interface AchievementView {

    /*  Данные достижения  */
    @ApiModelProperty(value = "id достижения. Not null. >0", example = "4")
    @JsonProperty("achieveId")
    int getId();

    @ApiModelProperty (value = "Название достижения. Not null", example = "Домовенок Кузя")
    @JsonProperty("achieveName")
    String getName();

    @ApiModelProperty (value = "Описание достижения. Not null", example = "Принять участие в субботнике")
    String getDescription();

    @ApiModelProperty (value = "Баллы начисляемые за выполнение достижения. Not null", example = "10")
    int getScore();

    @ApiModelProperty (value = "Статус активности достижения. Not null. [2,4]", example = "Устарело")
    @Value("#{target.statusActiveOfAchieve.statusActive}")
    @JsonProperty("statusActive")
    String getStatus();

    @ApiModelProperty (value = "Файл с изображением достижения. Not null")
    FileView getFile();


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


    /*  Данные пользователя - создателя достижения  */
    @ApiModelProperty(value = "Создатель достижения, может быть null")
    CreatorView getCreator();
    @ApiModel(description = "Создатель достижения, может быть null")
    interface CreatorView {
        @ApiModelProperty(value = "Фамилия пользователя - создателя достижений. Not null", example = "Антонов")
        String getFirstName();

        @ApiModelProperty(value = "Имя пользователя - создателя достижений. Not null", example = "Егор")
        String getLastName();
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
