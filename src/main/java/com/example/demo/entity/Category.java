package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

//Таблица категорий достижений
@Entity
@Table(name = "category", schema = "public")
public class Category {

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
    public int getIdCategory() {
        return id;
    }

    public void setIdCategory(int id) {
        this.id = id;
    }

    public String getNameCategory() {
        return name;
    }

    public void setNameCategory(String name) {
        this.name = name;
    }

    public File getFileOfCategory() {
        return file;
    }

    public void setFileOfCategory(File file) {
        this.file = file;
    }
}
