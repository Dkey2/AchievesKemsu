package com.example.demo.entity;

import javax.persistence.*;

//Таблица хранит виды операций, которые пользователь может осуществлять с таблицами, данными
@Entity
@Table(name = "type_operation", schema = "public")
public class Operation {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    //Геттеры, сеттеры
    public int getIdOperation() {
        return id;
    }

    public void setIdOperation(int id) {
        this.id = id;
    }

    public String getNameOperation() {
        return name;
    }

    public void setNameOperation(String name) {
        this.name = name;
    }
}
