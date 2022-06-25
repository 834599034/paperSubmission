package com.example.papersubmission.exceptions;

public class EmailException extends RuntimeException{
    public EmailException(){
        super("邮箱重复");
    }
}
