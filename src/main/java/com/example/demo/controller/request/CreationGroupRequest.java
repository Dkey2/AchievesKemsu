package com.example.demo.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel (description = "Данные, необходимые для создания группы")
public class CreationGroupRequest {

    @ApiModelProperty(value = "Название группы. Not null", example = "М-174")
    private String name;

    @ApiModelProperty (value = "Id направления, к которому относится данная группа. Not null. >0", example = "12")
    private int streamId;
}
