package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@ApiModel (description = "Конкретное подтверждение достижения для админа")
public interface ProofAdminView {

    /*  Данные подтверждения достижения  */
    @ApiModelProperty (value = "Id подтверждения достижения. Not null. >0", example = "32")
    @JsonProperty("proofId")
    int getId();

    @ApiModelProperty (value = "Дата отправления достижения. Not null", example = "2021-02-13")
    LocalDate getDateProof();

    @ApiModelProperty (value = "Комментарий к подтверждению достижения. Может быть null", example = "Молодец! Так держать")
    String getComment();

    @ApiModelProperty (value = "Статус рассмотрения заявки. Not null. [1,4]", example = "3")
    @Value("#{target.statusRequestProof.statusRequestName}")
    String getStatusRequestName();


    /*  Данные достижения  */
    AchieveView getAchievement();
    @ApiModel (description = "Данные достижения")
    interface AchieveView {

        @ApiModelProperty (value = "Id достижения. Not null. >0", example = "32")
        @JsonProperty("achieveId")
        int getId();

        @ApiModelProperty (value = "Название достижения. Not null", example = "Домовенок Кузя")
        @JsonProperty("achieveName")
        String getName();

        @ApiModelProperty (value = "Описание достижения. Not null", example = "Принять участие в субботнике")
        String getDescription();

        @ApiModelProperty (value = "Статус активности достижения. Not null", example = "Активно")
        @Value("#{target.statusActiveOfAchieve.statusActive}")
        @JsonProperty("statusActive")
        String getStatus();

        @ApiModelProperty (value = "Изображение достижения в виде byte[]. Not null")
        @Value("#{target.fileAchieve.dataFile}")
        byte[] getData();

        @ApiModelProperty (value = "Формат изображения достижения. Not null", example = "png")
        @Value("#{target.fileAchieve.formatFile}")
        String getFormat();
    }

    ListView getListFile();
    @ApiModel (description = "Id листа с файлами")
    interface ListView {
        @ApiModelProperty (value = "Id листа с файлами. Not null", example = "19")
        @JsonProperty("listFileId")
        int getId();
    }

    StudentView getStudent();
    @ApiModel (description = "Данные студента-отправителя")
    interface StudentView {
        @ApiModelProperty (value = "Id студента. Not null. >0", example = "3")
        @JsonProperty("studentId")
        int getId();
    }

    @Value("#{target.studentProof.userForStudent}")
    UserView getUser();
    @ApiModel (description = "Данные пользователя-отправителя")
    interface UserView {
        @ApiModelProperty (value = "Id пользователя. Not null. >0", example = "9")
        @JsonProperty("userId")
        int getId();

        @ApiModelProperty (value = "Имя студента. Not null", example = "Евгений")
        String getFirstName();

        @ApiModelProperty (value = "Фамилия студента. Not null", example = "Гарлов")
        String getLastName();
    }
}
