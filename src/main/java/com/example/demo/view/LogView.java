package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@ApiModel(description = "Конкретный лог для модератора (изменение фамилии/имени студентом)")
public interface LogView {

    @ApiModelProperty(value = "Id лога. Not null. >0", example = "21")
    @JsonProperty("logId")
    int getId();

    @ApiModelProperty (value = "Данные об изменениях. Not null", example = "studentId=12 - Петров Иван")
    String getNewData();

    @ApiModelProperty (value = "Старые данные. Not null", example = "Иванов Иван")
    String getOldData();

    @ApiModelProperty (value = "Id измененной записи/строки/элемента. Not null. >0", example = "7")
    int getRecordId();

    @ApiModelProperty (value = "Дата изменения. Not null", example = "2021-05-02")
    LocalDate getChangeDate();

    @ApiModelProperty (value = "Название типа операции. Not null", example = "Создание группы")
    @JsonProperty("operationName")
    @Value("#{target.operationLog.nameOperation}")
    String getName();
}
