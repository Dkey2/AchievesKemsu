package com.example.demo.entity;

import javax.persistence.*;

//Таблица для модераторов
@Entity
@Table(name = "moderator", schema = "public")
public class Moderator {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    //Связь с таблицами по foreign key
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "list_id")
    private ListInstitute listInstitute;

    //Геттеры, сеттеры
    public int getIdModerator() {
        return id;
    }

    public void setIdModerator(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ListInstitute getListInstituteForModerator() {
        return listInstitute;
    }

    public void setListInstituteForModerator(ListInstitute listInstitute) {
        this.listInstitute = listInstitute;
    }
}
