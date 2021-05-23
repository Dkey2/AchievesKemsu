package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel (description = "Список форм обучения")
public interface FormEducationVew {

    @ApiModelProperty (value = "Id формы обучения. Not null. [1,3]", example = "1")
    @JsonProperty("formEducationId")
    int getId();

    @ApiModelProperty (value = "Название формы обучения. Not null", example = "Бакалавриат")
    String getFormEducationName();
}
