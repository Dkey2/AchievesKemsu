package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel (description = "Список всех институтов")
public interface InstitutionsView {

    @ApiModelProperty (value = "Id института. Not null. [1,15]", example = "9")
    @JsonProperty("instituteId")
    int getId();

    @ApiModelProperty (value = "Краткое название института. Not null", example = "СПИ")
    String getInstituteName();
}
