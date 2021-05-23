package com.example.demo.entity;

import com.google.appengine.repackaged.com.google.common.flogger.annotations.LegacyContextReader;

import javax.persistence.*;

//Промежуточная таблица для связи подтверждения достижения с файлами. Создана, чтобы избежать связи МногиеКоМногим
@Entity
@Table(name = "list_file", schema = "public")
public class ListFile {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    //Геттеры, сеттеры
    public int getIdListFile() {
        return id;
    }

    public void setIdListFile(int id) {
        this.id = id;
    }
}
