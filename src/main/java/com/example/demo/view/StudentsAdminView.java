package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel(description = "Список студентов для админа")
public interface StudentsAdminView {
    @ApiModelProperty(value = "Id студента. Not null. >0", example = "3")
    @JsonProperty("studentId")
    int getId();

    @ApiModelProperty(value = "Фото профиля в виде byte[]. Not null")
    @Value("#{target.fileStudent.dataFile}")
    byte[] getData();

    @ApiModelProperty(value = "Формат фото профиля. Not null", example = "jpg")
    @Value("#{target.fileStudent.formatFile}")
    String getFormat();

    @ApiModelProperty(value = "Краткое название института. Not null", example = "ИФН")
    @Value("#{target.instituteStudent.instituteName}")
    String getInstituteName();

    @ApiModelProperty(value = "Краткое название направление. Not null", example = "ФИИТ")
    @Value("#{target.streamStudent.streamName}")
    String getStreamName();

    @ApiModelProperty(value = "Название группы. Not null", example = "М-174")
    @Value("#{target.groupStudent.groupName}")
    String getGroupName();

    @ApiModelProperty(value = "Название формы обучения. Not null", example = "Бакалавриат")
    @Value("#{target.formOfEducationStudent.formEducationName}")
    String getFormEducationName();

    @ApiModelProperty(value = "Имя пользователя. Not null", example = "Марина")
    @Value("#{target.userForStudent.firstNameUser}")
    String getFirstName();

    @ApiModelProperty(value = "Фамилия пользователя. Not null", example = "Бездетко")
    @Value("#{target.userForStudent.lastNameUser}")
    String getLastName();

    @Value("#{target.userForStudent.roleUser.nameRole}")
    @JsonProperty("roleName")
    String getRole();
}
