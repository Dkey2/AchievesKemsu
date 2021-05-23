package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Список типов операций")
public interface TypeOperationView {
    @ApiModelProperty (value = "Id типа операции. Not null. [1,16]", example = "3")
    @JsonProperty("operationId")
    int getId();

    @ApiModelProperty (value = "Наименование типа операции. Not null", example = "Изменение статуса сообщения об ошибке")
    @JsonProperty("operationName")
    String getName();
}
