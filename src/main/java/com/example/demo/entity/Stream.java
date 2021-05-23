package com.example.demo.entity;

import javax.persistence.*;

//Таблица направлений
@Entity
@Table(name = "stream", schema = "public")
public class Stream {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String streamName;
    @Column(name = "full_name")
    private String streamFullName;

    //Связь с таблицами по foreign key
    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    //Геттеры, сеттеры
    public int getIdStream() {
        return id;
    }

    public void setIdStream(int id) {
        this.id = id;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getStreamFullName() {
        return streamFullName;
    }

    public void setStreamFullName(String streamFullName) {
        this.streamFullName = streamFullName;
    }

    public Institute getInstituteForStream() {
        return institute;
    }

    public void setInstituteForStream(Institute institute) {
        this.institute = institute;
    }
}
