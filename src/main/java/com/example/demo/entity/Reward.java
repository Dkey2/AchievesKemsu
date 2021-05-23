package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

//Таблица наград за достижения (все награды)
@Entity
@Table(name = "reward", schema = "public")
public class Reward {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    //Связь с таблицами по foreign key
    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    //Геттеры, сеттеры
    public int getIdReward() {
        return id;
    }

    public void setIdReward(int id) {
        this.id = id;
    }

    public String getNameReward() {
        return name;
    }

    public void setNameReward(String name) {
        this.name = name;
    }

    public File getFileOfReward() {
        return file;
    }

    public void setFileOfReward(File file) {
        this.file = file;
    }
}
