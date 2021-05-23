package com.example.demo.entity;

import javax.persistence.*;

//Таблица для статусов активности/доступности достижения к выполнению (активно, не активно, устарело, не подтверждено)
@Entity
@Table(name = "status_active_achieve", schema = "public")
public class StatusActive {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String status;

    //Геттеры, сеттеры
    public int getIdStatusActive() {
        return id;
    }

    public void setIdStatusActive(int id) {
        this.id = id;
    }

    public String getStatusActive() {
        return status;
    }

    public void setStatusActive(String status) {
        this.status = status;
    }
}
