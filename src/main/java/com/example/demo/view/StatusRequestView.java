package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Список статусов рассмотрения заявки на подтверждение достижения")
public interface StatusRequestView {

    @ApiModelProperty(value = "Id статуса. Not null. [1,2]", example = "1")
    @JsonProperty("statusRequestId")
    int getId();

    @ApiModelProperty (value = "Название статуса. Not null", example = "Подтверждено")
    String getStatusRequestName();
}
