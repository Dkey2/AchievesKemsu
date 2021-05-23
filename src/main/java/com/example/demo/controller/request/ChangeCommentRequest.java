package com.example.demo.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel (description = "Данные, необходимые для изменения комментария к заявкам/сообщениям об ошибке")
public class ChangeCommentRequest {

    @ApiModelProperty (value = "Id заявки/сообщения об ошибке. Not null. >0", example = "21")
    int id;

    @ApiModelProperty (value = "Комментарий. Not null", example = "Прикрепленные файлы не подтверждают выполнение данного достижения")
    String comment;
}
