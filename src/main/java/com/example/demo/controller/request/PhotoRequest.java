package com.example.demo.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel (description = "Запрос с данными добавляемого файла")
public class PhotoRequest {

    @ApiModelProperty(notes = "Файл в виде byte[]. Not null", required = true)
    byte[] data;

    @ApiModelProperty(notes = "Формат файла. Not null", required = true, example = "pdf")
    String format;

    @ApiModelProperty(notes = "id листа, к которому прикрепляем файл. Not null. =0 для фото студента. >0 для подтверждения достижения", required = true, example = "3")
    int listFileId;
}
