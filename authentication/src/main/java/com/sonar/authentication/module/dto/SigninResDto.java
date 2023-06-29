package com.sonar.authentication.module.dto;

public class SigninResDto {
    private String status;
    private String token;
    private Integer userId;

    public SigninResDto(String status, String token, Integer userId) {
        this.status = status;
        this.token = token;
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
