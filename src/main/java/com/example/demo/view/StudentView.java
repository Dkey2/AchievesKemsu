package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel (description = "Данные профиля студента")
public interface StudentView {

    /*  Данные студента  */
    /*@ApiModelProperty (allowableValues = "range[1,infinity]", value = "Id студента. Not null. >0", example = "20")
    @JsonProperty("studentId")
    int getId();*/

    @ApiModelProperty (value = "Сумма баллов за все полученные достижения. Not null", example = "215")
    int getScore();

    @ApiModelProperty (value = "Фото профиля с виде байт массива. Not null", example = "0L/QstC6M9Cw0YE=")
    @Value("#{target.fileStudent.dataFile}")
    byte[] getData();

    @ApiModelProperty (value = "Формат фото профиля. Not null", example = "png")
    @Value("#{target.fileStudent.formatFile}")
    String getFormat();


    /*  Данные пользователя  */
    @ApiModelProperty (value = "Имя пользователя (этого студента). Not null", example = "Олег")
    @Value("#{target.userForStudent.firstNameUser}")
    String getFirstName();

    @ApiModelProperty (value = "Фамилия пользователя (этого студента). Not null", example = "Сафронов")
    @Value("#{target.userForStudent.lastNameUser}")
    String getLastName();

    @ApiModelProperty (value = "Статус активности пользователя. Not null", example = "Активен")
    @Value("#{target.userForStudent.statusUser.statusUser}")
    String getStatusUser();


    /*  Данные об образовании  */
    @ApiModelProperty (value = "Полное название института. Not null", example = "Институт фундаментальных наук")
    @Value("#{target.instituteStudent.instituteFullName}")
    String getInstituteFullName();

    @ApiModelProperty (value = "Полное название направления. Not null", example = "Фундаментальная информатика и информационные технологии")
    @Value("#{target.streamStudent.streamFullName}")
    String getStreamFullName();

    @ApiModelProperty (value = "Название группы. Not null", example = "М-174")
    @Value("#{target.groupStudent.groupName}")
    String getGroupName();

    @ApiModelProperty (value = "Название формы обучения. Not null", example = "Бакалавриат")
    @Value("#{target.formOfEducationStudent.formEducationName}")
    String getFormEducationName();
}
