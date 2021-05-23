package com.example.demo.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

@ApiModel (description = "Список всех категорий достижений")
public interface CategoriesView {

    @ApiModelProperty(value = "id категории. Not null. >0", example = "3")
    @JsonProperty("categoryId")
    int getId();

    @ApiModelProperty(value = "Название категории. Not null", example = "Наука")
    @JsonProperty("categoryName")
    String getName();

    @ApiModelProperty(value = "Иконка категории в виде byte[]. Not null")
    @Value("#{target.fileOfCategory.dataFile}")
    byte[] getData();

    @ApiModelProperty(value = "Формат иконки категории. Not null", example = "png")
    @Value("#{target.fileOfCategory.formatFile}")
    String getFormat();
}
