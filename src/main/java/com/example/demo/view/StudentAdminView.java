package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@ApiModel (description = "Конкретный студент для админа")
public interface StudentAdminView {
    @ApiModelProperty(value = "Id студента. Not null. >0", example = "3")
    @JsonProperty("studentId")
    int getId();

    @ApiModelProperty(value = "Баллы студента. Not null. >=0", example = "125")
    int getScore();

    @ApiModelProperty(value = "Фото профиля в виде byte[]. Not null")
    @Value("#{target.fileStudent.dataFile}")
    byte[] getData();

    @ApiModelProperty(value = "Формат фото профиля. Not null", example = "jpg")
    @Value("#{target.fileStudent.formatFile}")
    String getFormat();

    @ApiModelProperty(value = "Имя пользователя. Not null", example = "Марина")
    @Value("#{target.userForStudent.firstNameUser}")
    String getFirstName();

    @ApiModelProperty(value = "Фамилия пользователя. Not null", example = "Бездетко")
    @Value("#{target.userForStudent.lastNameUser}")
    String getLastName();

    @Value("#{target.userForStudent.roleUser.nameRole}")
    @JsonProperty("roleName")
    String getName();

    @ApiModelProperty(value = "Электронная почта пользователя. Not null", example = "qwert@mail.ru")
    @Value("#{target.userForStudent.emailUser}")
    String getEmail();

    @ApiModelProperty(value = "Статус пользователя. Not null", example = "Активен")
    @Value("#{target.userForStudent.statusUser.statusUser}")
    String getStatusUser();

    @ApiModelProperty(value = "Дата регистрации пользователя. Not null", example = "2021-01-01")
    @Value("#{target.userForStudent.dateRegistration}")
    LocalDate getDateRegistrationUser();

    @ApiModelProperty(value = "Полное название института. Not null", example = "Институт фундаментальных наук")
    @Value("#{target.instituteStudent.instituteFullName}")
    String getInstituteFullName();

    @ApiModelProperty(value = "Полное название направления. Not null", example = "Фундаментальная информатика и информационные технологии")
    @Value("#{target.streamStudent.streamFullName}")
    String getStreamFullName();

    @ApiModelProperty(value = "Название группы. Not null", example = "М-174")
    @Value("#{target.groupStudent.groupName}")
    String getGroupName();

    @ApiModelProperty(value = "Полное название института. Not null", example = "Институт фундаментальных наук")
    @Value("#{target.formOfEducationStudent.formEducationName}")
    String getFormEducationName();
}
