package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel (description = "Список сообщений об ошибке для студента")
public interface AllMessageErrorOfStudentView {

    @ApiModelProperty (value = "Id сообщения об ошибке. Not null. >0", example = "12")
    @JsonProperty("errorId")
    int getId();

    @ApiModelProperty (value = "Тема сообщения об ошибке", example = "Редактирование данных")
    String getTheme();

    @ApiModelProperty (value = "Статус рассмотрения сообщения об ошибке", example = "В обработке")
    @Value("#{target.statusMessageError.statusErrorName}")
    String getStatusErrorName();
}
