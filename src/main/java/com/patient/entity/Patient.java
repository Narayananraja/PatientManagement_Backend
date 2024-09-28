package com.patient.entity;

import jakarta.persistence.*;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String contact;
    private String disease;
    private String password;
    @Column(unique = true) // Ensuring that the email is unique
    private String email;


    public String getPassword() {
        return password;
    }

    public String getDisease() {
        return disease;
    }

    public String getContact() {
        return contact;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }


    public void setAge(int age) {
        this.age = age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
