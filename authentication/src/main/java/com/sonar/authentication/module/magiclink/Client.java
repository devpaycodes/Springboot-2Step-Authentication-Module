package com.sonar.authentication.module.magiclink;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String token;
    private Date craetedat;

    public Client(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCraetedat() {
        return craetedat;
    }

    public void setCraetedat(Date craetedat) {
        this.craetedat = craetedat;
    }

    public Client(String email, String token) {
        this.email = email;
        this.token = token;
        this.craetedat = new Date();
    }
}
