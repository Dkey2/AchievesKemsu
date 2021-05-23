package com.example.demo.entity;

import javax.persistence.*;

//Таблица для учебных групп
@Entity
@Table(name = "group", schema = "public")
public class Group {
    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String groupName;

    //Связь с таблицами по foreign key
    @ManyToOne
    @JoinColumn(name = "stream_id")
    private Stream stream;

    //Геттеры, сеттеры
    public int getIdGroup() {
        return id;
    }

    public void setIdGroup(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Stream getStreamForGroup() {
        return stream;
    }

    public void setStreamForGroup(Stream stream) {
        this.stream = stream;
    }
}
