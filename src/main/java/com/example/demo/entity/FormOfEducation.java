package com.example.demo.entity;

import javax.persistence.*;
//Таблица для форм обучения (бакалавриат, специалитет, магистратура)
@Entity
@Table(name = "form_education", schema = "public")
public class FormOfEducation {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String formEducationName;

    //Геттеры, сеттеры
    public int getIdFormEducation() {
        return id;
    }

    public void setIdFormEducation(int id) {
        this.id = id;
    }

    public String getFormEducationName() {
        return formEducationName;
    }

    public void setFormEducationName(String formEducationName) {
        this.formEducationName = formEducationName;
    }
}
