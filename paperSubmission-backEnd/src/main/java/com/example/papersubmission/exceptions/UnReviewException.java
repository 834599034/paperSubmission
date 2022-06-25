package com.example.papersubmission.exceptions;

public class UnReviewException extends  RuntimeException{
    public UnReviewException() {
        super("有审稿人未审稿完成");
    }
}
