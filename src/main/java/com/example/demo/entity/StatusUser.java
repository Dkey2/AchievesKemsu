package com.example.demo.entity;

import javax.persistence.*;

//Таблица для статуса активности пользователя (активен, удален, забанен)
@Entity
@Table(name = "status_user", schema = "public")
public class StatusUser {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String statusUser;

    //Геттеры, сеттеры
    public int getIdStatusUser() {
        return id;
    }

    public void setIdStatusUser(int id) {
        this.id = id;
    }

    public String getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }
}
