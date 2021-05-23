package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Список модераторов")
public interface ModersView {
    @JsonProperty("moderId")
    int getId();

    @ApiModelProperty(value = "Данные о пользователе")
    UserView getUser();
    interface UserView {
        @ApiModelProperty(value = "Id пользователя. Not null. >0", example = "27")
        @JsonProperty("userId")
        int getId();

        @ApiModelProperty(value = "Имя пользователя. Not null", example = "Егор")
        String getFirstName();

        @ApiModelProperty(value = "Фамилия пользователя. Not null", example = "Панфилов")
        String getLastName();
    }

    ListView getListInstitute();
    interface ListView {
        @ApiModelProperty(value = "Id листа институтов. Not null. >0", example = "12")
        @JsonProperty("listId")
        int getId();
    }
}
