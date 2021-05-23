package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel (description = "Лист файлов прикрепленный к подтверждению достижения")
public interface PhotoView {
    @ApiModelProperty (value = "id файла. Not null. >0", example = "17")
    @JsonProperty("fileId")
    int getId();

    @ApiModelProperty (value = "Файл в виде byte[]. Not null")
    byte[] getData();

    @ApiModelProperty (value = "Формат файла. Not null", example = "pdf")
    String getFormat();
}
