package com.example.demo.entity;

import javax.persistence.*;
import java.time.LocalDate;

//Таблица для логирования
@Entity
@Table(name = "log", schema = "public")
public class Log {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "change_date")
    private LocalDate changeDate;
    @Column(name = "record_id")
    private int recordId;
    @Column(name = "new_data")
    private String newData;
    @Column(name = "old_data")
    private String oldData;

    //Связь с таблицами по foreign key
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;

    //Геттеры, сеттеры
    public String getNewData() {
        return newData;
    }

    public void setNewData(String newData) {
        this.newData = newData;
    }

    public String getOldData() {
        return oldData;
    }

    public void setOldData(String oldData) {
        this.oldData = oldData;
    }

    public int getIdLog() {
        return id;
    }

    public void setIdLog(int id) {
        this.id = id;
    }

    public LocalDate getChangeDateLog() {
        return changeDate;
    }

    public void setChangeDateLog(LocalDate changeDate) {
        this.changeDate = changeDate;
    }

    public int getRecordIdLog() {
        return recordId;
    }

    public void setRecordIdLog(int recordId) {
        this.recordId = recordId;
    }

    public User getUserLog() {
        return user;
    }

    public void setUserLog(User user) {
        this.user = user;
    }

    public Operation getOperationLog() {
        return operation;
    }

    public void setOperationLog(Operation operation) {
        this.operation = operation;
    }
}
