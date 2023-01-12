package com.example.pirate99_final.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private int statusCode;
    private String msg;

    public CustomException(ErrorCode errorCode) {
        this.statusCode = errorCode.getHttpStatus().value();
        this.msg = errorCode.getMessage();
    }

    public CustomException(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

}