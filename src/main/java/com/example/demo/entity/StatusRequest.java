package com.example.demo.entity;

import javax.persistence.*;

//Таблица для статусов рассмотрения заявок на получение и добавления нового достижения
@Entity
@Table(name = "status_request", schema = "public")
public class StatusRequest {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String statusRequestName;

    //Геттеры, сеттеры
    public int getIdStatusRequest() {
        return id;
    }

    public void setIdStatusRequest(int id) {
        this.id = id;
    }

    public String getStatusRequestName() {
        return statusRequestName;
    }

    public void setStatusRequestName(String statusRequestName) {
        this.statusRequestName = statusRequestName;
    }
}
