package com.example.demo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDate;

//Таблица достижений, находящихся в системе (все достижения)
@Entity
@Table(name = "achievement", schema = "public")
public class Achievement {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "score")
    private int score;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    //Связь с таблицами по foreign key
    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;
    @ManyToOne
    @JoinColumn(name = "reward_id")
    private Reward reward;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "status_active_id")
    private StatusActive statusActive;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    //Геттеры, сеттеры
    public int getIdAchieve() {
        return id;
    }

    public void setIdAchieve(int id) {
        this.id = id;
    }

    public String getNameAchieve() {
        return name;
    }

    public void setNameAchieve(String name) {
        this.name = name;
    }

    public String getDescriptionAchieve() {
        return description;
    }

    public void setDescriptionAchieve(String description) {
        this.description = description;
    }

    public int getScoreAchieve() {
        return score;
    }

    public void setScoreAchieve(int score) {
        this.score = score;
    }

    public LocalDate getStartDateAchieve() {
        return startDate;
    }

    public void setStartDateAchieve(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDateAchieve() {
        return endDate;
    }

    public void setEndDateAchieve(LocalDate endDate) {
        this.endDate = endDate;
    }

    public File getFileAchieve() {
        return file;
    }

    public void setFileAchieve(File file) {
        this.file = file;
    }

    public Reward getRewardOfAchieve() {
        return reward;
    }

    public void setRewardOfAchieve(Reward reward) {
        this.reward = reward;
    }

    public Category getCategoryOfAchieve() {
        return category;
    }

    public void setCategoryOfAchieve(Category category) {
        this.category = category;
    }

    public StatusActive getStatusActiveOfAchieve() {
        return statusActive;
    }

    public void setStatusActiveOfAchieve(StatusActive statusActive) {
        this.statusActive = statusActive;
    }

    public User getCreatorOfAchieve() {
        return creator;
    }

    public void setCreatorOfAchieve(User creator) {
        this.creator = creator;
    }
}
