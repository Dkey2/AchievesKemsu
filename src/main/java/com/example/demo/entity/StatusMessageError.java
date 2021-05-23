package com.example.demo.entity;

import javax.persistence.*;

//Таблица для статусов рассмотрения сообщения об ошибке
@Entity
@Table(name = "status_error", schema = "public")
public class StatusMessageError {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String statusErrorName;

    //Геттеры, сеттеры
    public int getIdStatusError() {
        return id;
    }

    public void setIdStatusError(int id) {
        this.id = id;
    }

    public String getStatusErrorName() {
        return statusErrorName;
    }

    public void setStatusErrorName(String statusErrorName) {
        this.statusErrorName = statusErrorName;
    }
}
