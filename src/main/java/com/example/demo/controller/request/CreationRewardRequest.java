package com.example.demo.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel (description = "Данные, необходимые для создание новой награды")
public class CreationRewardRequest {

    @ApiModelProperty (value = "Название награды. Not null", example = "Билет в кинотеатр")
    String rewardName;

    @ApiModelProperty (value = "Иконка награды в виде byte[]. Not null")
    byte[] data;

    @ApiModelProperty (value = "Формат иконки награды. Not null", example = "png")
    String format;
}
