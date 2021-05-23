package com.example.demo.entity;

import javax.persistence.*;
import java.time.LocalDate;

//Таблица пользователей
@Entity
@Table(name = "user", schema = "public")
public class User {

    //Колонки без foreign key
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_registration")
    private LocalDate dateRegistration;

    //Связь с таблицами по foreign key
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusUser statusUser;

    //Геттеры, сеттеры
    public int getIdUser() {
        return id;
    }

    public void setIdUser(int id) {
        this.id = id;
    }

    public String getEmailUser() {
        return email;
    }

    public void setEmailUser(String email) {
        this.email = email;
    }

    public String getPasswordUser() {
        return password;
    }

    public void setPasswordUser(String password) {
        this.password = password;
    }

    public String getFirstNameUser() {
        return firstName;
    }

    public void setFirstNameUser(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNameUser() {
        return lastName;
    }

    public void setLastNameUser(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateRegistrationUser() {
        return dateRegistration;
    }

    public void setDateRegistrationUser(LocalDate dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public Role getRoleUser() {
        return role;
    }

    public void setRoleUser(Role role) {
        this.role = role;
    }

    public StatusUser getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(StatusUser statusUser) {
        this.statusUser = statusUser;
    }
}
