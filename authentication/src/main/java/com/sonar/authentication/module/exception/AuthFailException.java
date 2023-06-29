package com.sonar.authentication.module.exception;

public class AuthFailException extends IllegalArgumentException{
    public  AuthFailException(String msg){
        super(msg);
    }
}
