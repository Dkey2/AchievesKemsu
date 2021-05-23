package com.example.demo.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel (description = "Данные об образовании студента для профиля")
public interface StudyView {

    @ApiModelProperty (value = "Краткое название института. Not null", example = "ИФН")
    @Value("#{target.instituteStudent.instituteName}")
    String getInstituteName();

    @ApiModelProperty (value = "Краткое название направления. Not null", example = "ФИИТ")
    @Value("#{target.streamStudent.streamName}")
    String getStreamName();

    @ApiModelProperty (value = "Название группы. Not null", example = "М-174")
    @Value("#{target.groupStudent.groupName}")
    String getGroupName();

    @ApiModelProperty (value = "Название формы обучения. Not null", example = "Бакалавриат")
    @Value("#{target.formOfEducationStudent.formEducationName}")
    String getFormEducationName();
}
