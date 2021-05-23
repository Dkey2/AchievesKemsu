package com.example.demo.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@ApiModel (description = "Данные, необходимые для создания бана")
public class CreationBanRequest {

    @ApiModelProperty (value = "Id студента, которому выдается бан. Not null. >0", example = "12")
    int studentId;

    @ApiModelProperty (value = "Дата, когда истекает время действия бана. Not null", example = "2021-03-20")
    LocalDate banDateEnd;

    @ApiModelProperty (value = "Причина бана. Not null", example = "Использование ненормативной лексики")
    String banReason;
}
