package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel(description = "Список достижений в системе для администратора")
public interface AllAchievementsForAdminView {

    /*  Данные достижения  */
    @ApiModelProperty(value = "id достижения", example = "4")
    @JsonProperty("achieveId")
    int getId();

    @ApiModelProperty (value = "Название достижения", example = "Домовенок Кузя")
    @JsonProperty("achieveName")
    String getName();

    @ApiModelProperty (value = "Описание достижения", example = "Принять участие в субботнике")
    String getDescription();

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


    /*  Данные пользователя-создателя достижения  */
    CreatorView getCreator();
    interface CreatorView {
        @ApiModelProperty(value = "id пользователя-создателя. Not null. >0", example = "15")
        int getId();

        @ApiModelProperty(value = "Имя пользователя-создателя. Not null", example = "Максим")
        String getFirstName();

        @ApiModelProperty(value = "Фамилия пользователя-создателя. Not null", example = "Паркин")
        String getLastName();

        @ApiModelProperty(value = "Статус пользователя-создателя. Not null", example = "Забанен")
        @Value("#{target.statusUser.statusUser}")
        String getStatusUser();
    }


    /*  Данные файла с изображением  */
    @ApiModel(description = "Данные файла: изображение и формат")
    interface FileView {

        @ApiModelProperty (value = "Id изображения. Not null. >0", example = "4")
        @JsonProperty("fileId")
        byte[] getId();

        @ApiModelProperty (value = "Изображение в виде byte[]. Not null")
        byte[] getData();

        @ApiModelProperty (value = "Формат изображения. Not null", example = "jpg")
        String getFormat();
    }
}
