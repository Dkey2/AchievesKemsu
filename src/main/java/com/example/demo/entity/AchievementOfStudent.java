package com.example.demo.entity;

import javax.persistence.*;

//Таблица содержит достижения, которые получил конкретный студент
@Entity
@Table(name = "achievement_student", schema = "public")
public class AchievementOfStudent {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "status_reward")
    private boolean statusReward;

    //Связь с таблицами по foreign key
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "achieve_id")
    private Achievement achievement;

    //Геттеры, сеттеры
    public int getIdAchieveOfStudent() {
        return id;
    }

    public void setIdAchieveOfStudent(int id) {
        this.id = id;
    }

    public boolean getStatusRewardForAchieveOfStudent() {
        return statusReward;
    }

    public void setStatusRewardForAchieveOfStudent(boolean statusReward) {
        this.statusReward = statusReward;
    }

    public Student getStudentForAchieveOfStudent() {
        return student;
    }

    public void setStudentForAchieveOfStudent(Student student) {
        this.student = student;
    }

    public Achievement getAchievementForAchieveOfStudent() {
        return achievement;
    }

    public void setAchievementForAchieveOfStudent(Achievement achievement) {
        this.achievement = achievement;
    }
}
