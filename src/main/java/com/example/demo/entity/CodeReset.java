package com.example.demo.entity;

import javax.persistence.*;

//Таблица кодов для восстановления пароля/аккаунта
@Entity
@Table(name = "code_reset", schema = "public")
public class CodeReset {

    @Id
    private String codeReset;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getCodeReset() {
        return codeReset;
    }

    public void setCodeReset(String codeReset) {
        this.codeReset = codeReset;
    }

    public User getUserCodeReset() {
        return user;
    }

    public void setUserCodeReset(User user) {
        this.user = user;
    }
}
