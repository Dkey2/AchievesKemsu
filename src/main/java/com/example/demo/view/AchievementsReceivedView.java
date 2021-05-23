package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel (description = "Список достижений, полученных авторизованным студентом")
public interface AchievementsReceivedView {

    /*  Данные полученного достижения  */
    @ApiModelProperty(value = "id полученного достижения. Not null. >0", example = "15")
    @JsonProperty("receivedAchieveId")
    int getId();

    @ApiModelProperty(value = "Статус награды полученного достижения (получена награда или нет). Not null", example = "false")
    boolean getStatusReward();


    /*  Данные достижения системы  */
    AchieveView getAchievement();
    @ApiModel(description = "Данные достижения в системе")
    interface AchieveView {
        @ApiModelProperty(value = "id достижения. Not null. >0", example = "4")
        @JsonProperty("achieveId")
        int getId();

        @ApiModelProperty(value = "Название достижения. Not null", example = "Домовенок Кузя")
        @JsonProperty("achieveName")
        String getName();

        @ApiModelProperty(value = "Описание достижения. Not null", example = "Принять участие в субботнике")
        String getDescription();

        @ApiModelProperty(value = "Баллы начисляемые за выполнение достижения. Not null. >0", example = "10")
        int getScore();

        @ApiModelProperty(value = "Файл с изображением достижения")
        FileView getFile();

        @ApiModelProperty(value = "Статус активности достижения. Not null. [2,4]", example = "Устарело")
        @Value("#{target.statusActiveOfAchieve.statusActive}")
        @JsonProperty("statusActive")
        String getStatus();
    }


    /*  Данные награды  */;
    @Value("#{target.achievementForAchieveOfStudent.rewardOfAchieve}")
    RewardView getReward();
    @ApiModel(description = "Данные награды за достижение")
    interface RewardView {
        @ApiModelProperty(value = "Файл с иконкой награды")
        FileView getFile();
    }

    /*  Данные категории  */
    @Value("#{target.achievementForAchieveOfStudent.categoryOfAchieve}")
    CategoryView getCategory();

    @ApiModel(description = "Данные награды за достижение")
    interface CategoryView {
        @ApiModelProperty(value = "Файл с иконкой категории")
        FileView getFile();
    }


    /*  Данные файла с изображением  */
    @ApiModel(description = "Данные файла: изображение и формат")
    interface FileView {
        @ApiModelProperty (value = "Изображение в виде byte[]. Not null")
        byte[] getData();

        @ApiModelProperty (value = "Формат изображения. Not null", example = "jpg")
        String getFormat();
    }
}
