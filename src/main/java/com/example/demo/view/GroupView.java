package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel (description = "Список групп для раннее выбранного направления")
public interface GroupView {

    @ApiModelProperty(value = "Id группы. Not null. >0", example = "12")
    @JsonProperty("groupId")
    int getId();

    @ApiModelProperty(value = "Название группы. Not null", example = "РСО-201")
    String getGroupName();
}
