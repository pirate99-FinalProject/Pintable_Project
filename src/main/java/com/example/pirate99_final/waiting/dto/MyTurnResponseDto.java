package com.example.pirate99_final.waiting.dto;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class MyTurnResponseDto extends MsgResponseDto {

    private int totalWaitingCnt;

    private int myTurn;

    private int statusCode;
    private String msg;

    public MyTurnResponseDto(int totalWaitingCnt, int myTurn) {
        this.totalWaitingCnt = totalWaitingCnt;
        this.myTurn = myTurn;
    }
    public MyTurnResponseDto(ErrorCode errorCode) {
        this.msg        =   errorCode.getMessage();
        this.statusCode =   errorCode.getHttpStatus().value();
    }
}
