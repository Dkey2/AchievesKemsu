package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@ApiModel(description = "Конкретная заявка на получение достижения")
public interface ProofView {

    @ApiModelProperty (value = "id заявки. Not null. >0", example = "1")
    @JsonProperty("proofId")
    int getId();

    @ApiModelProperty (value = "Дата отправления заявки. Not null", example = "2021-01-17")
    LocalDate getDateProof();

    @ApiModelProperty (value = "Описание причины, почему студент должен получить это достижение. Not null", example = "Я умничка")
    String getDescription();

    @ApiModelProperty (value = "Комментарий модератора к заявке. Not null", example = "Вовсе ты и  не умничка")
    String getComment();

    @ApiModelProperty (value = "Статус обработки заявки. Not null", example = "Подтверждено")
    @Value("#{target.statusRequestProof.statusRequestName}")
    String getStatusRequestName();

    ListView getListFile();
    @ApiModel (description = "id листа с прикрепленными к подтверждению файлами")
    interface ListView {
        @ApiModelProperty (value = "id листа с прикрепленными к подтверждению файлами. Not null. >0", example = "12")
        @JsonProperty("listFileId")
        int getId();
    }

    AchieveView getAchievement();
    @ApiModel (description = "Достижение, которое пользователь хочет получить")
    interface AchieveView {
        @ApiModelProperty (value = "id достижения. Not null. >0", example = "4")
        @JsonProperty("achieveId")
        int getId();

        @ApiModelProperty (value = "Название достижения. Not null", example = "Цезарь")
        @JsonProperty("achieveName")
        String getName();

        @ApiModelProperty (value = "Описание достижения. Not null", example = "Официально устроиться на работу во время обучения")
        String getDescription();

        @ApiModelProperty (value = "Изображение достижения в виде byte[]. Not null")
        @Value("#{target.fileAchieve.dataFile}")
        byte[] getData();

        @ApiModelProperty (value = "Формат изображения достижения. Not null", example = "png")
        @Value("#{target.fileAchieve.formatFile}")
        String getFormat();
    }
}
