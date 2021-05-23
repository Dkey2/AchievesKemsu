package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@ApiModel (description = "Конкретное достижение для модератора")
public interface AchievementAdminView {

    /*  Данные достижения  */
    @ApiModelProperty (value = "id достижения. Not null. >0", example = "4")
    @JsonProperty("achieveId")
    int getId();

    @ApiModelProperty (value = "Название достижения. Not null", example = "Домовенок Кузя")
    @JsonProperty("achieveName")
    String getName();

    @ApiModelProperty (value = "Описание достижения. Not null", example = "Принять участие в субботнике")
    String getDescription();

    @ApiModelProperty (value = "Дата, начиная с которой можно подтверждать данное достижение. Not null", example = "2021-03-23")
    LocalDate getStartDate();

    @ApiModelProperty (value = "Дата, начиная с которой уже нельзя подтверждать данное достижение. Может быть null", example = "null")
    LocalDate getEndDate();

    @ApiModelProperty (value = "Статус активности достижения. Not null", example = "Устарело")
    @Value("#{target.statusActiveOfAchieve.statusActive}")
    @JsonProperty("statusActive")
    String getStatus();

    @ApiModelProperty (value = "Файл с изображением достижения")
    FileView getFile();


    /*  Данные награды  */
    RewardView getReward();
    interface RewardView {
        @ApiModelProperty(value = "id награды. Not null. >0", example = "8")
        @JsonProperty("rewardId")
        int getId();

        @ApiModelProperty(value = "Название награды. Not null", example = "Бесплатный кофе")
        @JsonProperty("rewardName")
        String getName();

        @ApiModelProperty(value = "Файл с иконкой награды")
        FileView getFile();
    }


    /*  Данные пользователя-создателя достижения  */
    CreatorView getCreator();
    interface CreatorView {
        @ApiModelProperty(value = "id пользователя-создателя. Not null. >0", example = "15")
        @JsonProperty("creatorId")
        int getId();

        @ApiModelProperty(value = "Имя пользователя-создателя. Not null", example = "Максим")
        String getFirstName();

        @ApiModelProperty(value = "Фамилия пользователя-создателя. Not null", example = "Паркин")
        String getLastName();

        @ApiModelProperty(value = "Статус пользователя-создателя. Not null", example = "Забанен")
        @Value("#{target.statusUser.statusUser}")
        String getStatusUser();
    }


    /*  Данные категории  */
    CategoryView getCategory();
    interface CategoryView {
        @ApiModelProperty(value = "id категории. Not null. >0", example = "2")
        @JsonProperty("categoryId")
        int getId();

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
