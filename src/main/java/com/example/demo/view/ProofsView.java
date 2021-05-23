package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@ApiModel(description = "Список заявок на подтверждение достижения")
public interface ProofsView {

    @ApiModelProperty(value = "id заявки", example = "1")
    @JsonProperty("proofId")
    int getId();

    @ApiModelProperty (value = "Дата отправления заявки", example = "2021-01-17")
    LocalDate getDateProof();

    @ApiModelProperty (value = "Статус обработки заявки", example = "Отклонено")
    @Value("#{target.statusRequestProof.statusRequestName}")
    String getStatusRequestName();


    /*  Данные достижения  */
    AchieveView getAchievement();
    @ApiModel(description = "Данные достижения")
    interface AchieveView {

        @ApiModelProperty (value = "Id достижения. Not null. >0", example = "32")
        @JsonProperty("achieveId")
        int getId();

        @ApiModelProperty (value = "Название достижения. Not null", example = "Домовенок Кузя")
        @JsonProperty("achieveName")
        String getName();

        @ApiModelProperty (value = "Изображение достижения в виде byte[]. Not null")
        @Value("#{target.fileAchieve.dataFile}")
        byte[] getData();

        @ApiModelProperty (value = "Формат изображения достижения. Not null", example = "png")
        @Value("#{target.fileAchieve.formatFile}")
        String getFormat();
    }

}
