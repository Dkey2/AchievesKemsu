package com.example.demo.entity;

import javax.persistence.*;

//Промежуточная таблица для связи модераторов с институтами. Создана, чтобы избежать связи МногиеКоМногим
@Entity
@Table(name = "list_institute", schema = "public")
public class ListInstitute {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    //Геттеры, сеттеры
    public Integer getIdListInstitute() {
        return id;
    }

    public void setIdListInstitute(Integer id) {
        this.id = id;
    }
}
