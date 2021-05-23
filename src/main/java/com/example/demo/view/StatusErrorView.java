package com.example.demo.view;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel (description = "Список статусов сообщений об ошибке")
public interface StatusErrorView {

    @ApiModelProperty(value = "Id статуса рассмотрения сообщения об ошибке. Not null. [1,4]", example = "3")
    @JsonProperty("statusErrorId")
    int getId();

    @ApiModelProperty(value = "Статус рассмотрения сообщения об ошибке. Not null", example = "В обработке")
    String getStatusErrorName();
}
