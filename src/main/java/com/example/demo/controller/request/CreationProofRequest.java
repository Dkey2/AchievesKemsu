package com.example.demo.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(description = "Запрос с данными для подтверждения достижения")
public class CreationProofRequest {

    @ApiModelProperty(notes = "Описание причины, почему студент должен получить это достижение. Not null", example = "Я самый умный на планете")
    String description;

    @ApiModelProperty(notes = "Количество прикрепляемых файлов. Not null. >=0", example = "2")
    int files;

    @ApiModelProperty(notes = "id достижения, которое студент хочет получить. Not null. >0", example = "9")
    int achieveId;
}
