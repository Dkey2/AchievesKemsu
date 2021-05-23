package com.example.demo.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ban", schema = "public")
public class Ban {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "reason")
    private String reason;
    @Column(name = "date")
    private LocalDate dateEndBan;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userBanned;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creatorBan;

    public int getIdBan() {
        return id;
    }

    public void setIdBan(int id) {
        this.id = id;
    }

    public String getReasonBan() {
        return reason;
    }

    public void setReasonBan(String reason) {
        this.reason = reason;
    }

    public LocalDate getDateEndBan() {
        return dateEndBan;
    }

    public void setDateEndBan(LocalDate dateEndBan) {
        this.dateEndBan = dateEndBan;
    }

    public User getUserBanned() {
        return userBanned;
    }

    public void setUserBanned(User userBanned) {
        this.userBanned = userBanned;
    }

    public User getCreatorBan() {
        return creatorBan;
    }

    public void setCreatorBan(User creatorBan) {
        this.creatorBan = creatorBan;
    }
}
