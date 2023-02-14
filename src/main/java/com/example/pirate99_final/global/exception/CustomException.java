package com.example.pirate99_final.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
    public CustomException(int statusCode, ErrorCode errorCode) {
        this.msg            =   msg;                                // 안내 문구
        this.statusCode     =   statusCode;                         // 상태 코드
    }
    public CustomException(MethodArgumentNotValidException ex) {
        this.msg        =   ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        this.statusCode =   HttpStatus.BAD_REQUEST.value();
    }

}