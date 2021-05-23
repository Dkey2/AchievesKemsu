package com.example.demo.entity;

import javax.persistence.*;

//Таблица для хранения всех файлов, картинок, иконок и т.д. Все хранится в зашифрованном(Base64) массиве байтов
@Entity
@Table(name = "file", schema = "public")
public class File {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "file")
    private byte[] data;
    @Column(name = "format")
    private String format;

    //Связь с таблицами по foreign key
    @ManyToOne
    @JoinColumn(name = "list_id")
    private ListFile listFile;

    //Геттеры, сеттеры
    public int getIdFile() {
        return id;
    }

    public void setIdFile(int id) {
        this.id = id;
    }

    public byte[] getDataFile() {
        return data;
    }

    public void setDataFile(byte[] data) {
        this.data = data;
    }

    public String getFormatFile() {
        return format;
    }

    public void setFormatFile(String format) {
        this.format = format;
    }

    public ListFile getListFile() {
        return listFile;
    }

    public void setListFile(ListFile listFile) {
        this.listFile = listFile;
    }
}
