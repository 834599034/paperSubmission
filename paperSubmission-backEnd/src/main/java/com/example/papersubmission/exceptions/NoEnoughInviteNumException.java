package com.example.papersubmission.exceptions;

public class NoEnoughInviteNumException extends  RuntimeException{
    public NoEnoughInviteNumException(String message) {
        super(message);
    }

    public NoEnoughInviteNumException() {
        super("无剩余邀请人数");
    }
}
