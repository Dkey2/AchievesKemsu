package com.example.demo.entity;

import javax.persistence.*;
import java.time.LocalDate;

//Таблица хранит заявки на подтверждение достижения
@Entity
@Table(name = "proof_achieve", schema = "public")
public class ProofAchieve {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "date_proof")
    private LocalDate dateProof;
    @Column(name = "description")
    private String description;
    @Column(name = "comment")
    private String comment;

    //Связь с таблицами по foreign key
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "list_id")
    private ListFile listFile;
    @ManyToOne
    @JoinColumn(name = "achieve_id")
    private Achievement achievement;
    @ManyToOne
    @JoinColumn(name = "status_request_id")
    private StatusRequest statusRequest;

    //Геттеры, сеттеры
    public int getIdProof() {
        return id;
    }

    public void setIdProof(int id) {
        this.id = id;
    }

    public LocalDate getDateProof() {
        return dateProof;
    }

    public void setDateProof(LocalDate dateProof) {
        this.dateProof = dateProof;
    }

    public String getDescriptionProof() {
        return description;
    }

    public void setDescriptionProof(String description) {
        this.description = description;
    }

    public String getCommentProof() {
        return comment;
    }

    public void setCommentProof(String comment) {
        this.comment = comment;
    }

    public Student getStudentProof() {
        return student;
    }

    public void setStudentProof(Student student) {
        this.student = student;
    }

    public ListFile getListFileForProof() {
        return listFile;
    }

    public void setListFileForProof(ListFile listFile) {
        this.listFile = listFile;
    }

    public Achievement getAchievementForProof() {
        return achievement;
    }

    public void setAchievementForProof(Achievement achievement) {
        this.achievement = achievement;
    }

    public StatusRequest getStatusRequestProof() {
        return statusRequest;
    }

    public void setStatusRequestProof(StatusRequest statusRequest) {
        this.statusRequest = statusRequest;
    }
}
