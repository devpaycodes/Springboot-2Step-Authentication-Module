package com.sonar.authentication.module;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="tokens")
public class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int Id;

    private String token;

    @Column(name = "created_date")
    private Date craetedDate;

    @OneToOne(targetEntity = User.class, fetch=FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCraetedDate() {
        return craetedDate;
    }

    public void setCraetedDate(Date craetedDate) {
        this.craetedDate = craetedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthToken(User user) {
        this.user = user;
        this.craetedDate = new Date();
        this.token = UUID.randomUUID().toString();
    }

    public AuthToken() {
    }
}
