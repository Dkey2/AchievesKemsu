package com.example.demo.entity;

import javax.persistence.*;

//Таблица для ролей пользователя
@Entity
@Table(name = "role", schema = "public")
public class Role {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    //Геттеры, сеттеры
    public int getIdRole() {
        return id;
    }

    public void setIdRole(int id) {
        this.id = id;
    }

    public String getNameRole() {
        return name;
    }

    public void setNameRole(String name) {
        this.name = name;
    }
}
