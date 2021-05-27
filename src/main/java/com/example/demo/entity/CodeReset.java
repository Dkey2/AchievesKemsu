package com.example.demo.entity;

import javax.persistence.*;

//Таблица кодов для восстановления пароля/аккаунта
@Entity
@Table(name = "code_reset", schema = "public")
public class CodeReset {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUserCodeReset() {
        return user;
    }

    public void setUserCodeReset(User user) {
        this.user = user;
    }
}
