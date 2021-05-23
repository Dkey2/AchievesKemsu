package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel (description = "Список направлений для раннее выбранного института")
public interface StreamView {

    @ApiModelProperty (value = "Id направления. Not null. >0", example = "9")
    @JsonProperty("streamId")
    int getId();

    @ApiModelProperty (value = "Краткое название направления. Not null", example = "РСО")
    String getStreamName();
}
