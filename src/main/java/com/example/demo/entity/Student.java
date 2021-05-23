package com.example.demo.entity;

import javax.persistence.*;

//Таблица студентов
@Entity
@Table(name = "student", schema = "public")
public class Student {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "score")
    private int score;

    //Связь с таблицами по foreign key
    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;
    @ManyToOne
    @JoinColumn(name = "stream_id")
    private Stream stream;
    @ManyToOne
    @JoinColumn(name = "form_education_id")
    private FormOfEducation formOfEducation;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    //Геттеры, сеттеры

    public int getIdStudent() {
        return id;
    }

    public void setIdStudent(int id) {
        this.id = id;
    }

    public int getScoreStudent() {
        return score;
    }

    public void setScoreStudent(int score) {
        this.score = score;
    }

    public File getFileStudent() {
        return file;
    }

    public void setFileStudent(File file) {
        this.file = file;
    }

    public User getUserForStudent() {
        return user;
    }

    public void setUserForStudent(User user) {
        this.user = user;
    }

    public Institute getInstituteStudent() {
        return institute;
    }

    public void setInstituteStudent(Institute institute) {
        this.institute = institute;
    }

    public Stream getStreamStudent() {
        return stream;
    }

    public void setStreamStudent(Stream stream) {
        this.stream = stream;
    }

    public FormOfEducation getFormOfEducationStudent() {
        return formOfEducation;
    }

    public void setFormOfEducationStudent(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public Group getGroupStudent() {
        return group;
    }

    public void setGroupStudent(Group group) {
        this.group = group;
    }
}
