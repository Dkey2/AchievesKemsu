package com.example.demo.entity;

import javax.persistence.*;

//Таблица институтов
@Entity
@Table(name = "institute", schema = "public")
public class Institute {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String instituteName;
    @Column(name = "full_name")
    private String instituteFullName;

    //Связь с таблицами по foreign key
    @ManyToOne
    @JoinColumn(name = "list_id")
    private ListInstitute listInstitute;

    //Геттеры, сеттеры
    public int getIdInstitute() {
        return id;
    }

    public void setIdInstitute(int id) {
        this.id = id;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getInstituteFullName() {
        return instituteFullName;
    }

    public void setInstituteFullName(String instituteFullName) {
        this.instituteFullName = instituteFullName;
    }

    public ListInstitute getListInstituteForInstitute() {
        return listInstitute;
    }

    public void setListInstituteForInstitute(ListInstitute listInstitute) {
        this.listInstitute = listInstitute;
    }
}
