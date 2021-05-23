package com.example.demo.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel (description = "Данные, необходиме для создания направления")
public class CreationStreamRequest {

    @ApiModelProperty (value = "Краткое название направления. Not null", example = "ФИИТ")
    private String shortName;

    @ApiModelProperty (value = "Полное название направления. Not null", example = "Фундаментальная информатика и информационные технологии")
    private String fullName;

    @ApiModelProperty (value = "Id института, к которому относится данное направление. Not null. [1,15]", example = "1")
    private int instituteId;
}
