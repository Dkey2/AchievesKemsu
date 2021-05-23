package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel (description = "Список всех наград за выполнение достижений")
public interface RewardsView {

    @ApiModelProperty (value = "Id награды. Not null. [1, ?]", example = "7")
    @JsonProperty("rewardId")
    int getId();

    @ApiModelProperty (value = "Название награды. Not null", example = "Бесплатный кофе")
    @JsonProperty("rewardName")
    String getName();

    @ApiModelProperty (value = "Иконка награды в формате byte[]. Not null")
    @Value("#{target.fileOfReward.dataFile}")
    byte[] getData();

    @ApiModelProperty (value = "Формат иконки награды. Not null", example = "jpg")
    @Value("#{target.fileOfReward.formatFile}")
    String getFormat();
}
