package com.example.pirate99_final.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private String msg;                                             // 안내 문구
    private int statusCode;                                         // 상태 코드


    //Constructor
    public CustomException(ErrorCode errorCode) {
        this.msg            =   errorCode.getMessage();             // 안내 문구
        this.statusCode     =   errorCode.getHttpStatus().value();  // 상태 코드
    }

    public CustomException(int statusCode, String msg) {
        this.msg            =   msg;                                // 안내 문구
        this.statusCode     =   statusCode;                         // 상태 코드
    }

}