package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Список статусов активности достижений")
public interface StatusActiveView {

    @ApiModelProperty (value = "Id статуса активности достижения. Not null. [1,6] для админа. [5,6] для модератора", example = "3")
    @JsonProperty("statusActiveId")
    int getId();

    @ApiModelProperty (value = "Статус активности достижения", example = "Одобрено")
    @JsonProperty("statusActiveName")
    String getStatus();
}
