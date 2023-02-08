package com.example.pirate99_final.waiting.dto;

import com.example.pirate99_final.global.MsgResponseDto;
import lombok.Getter;

@Getter
public class MyTurnResponseDto extends MsgResponseDto {

    private int totalWaitingCnt;

    private int myTurn;


    public MyTurnResponseDto(int totalWaitingCnt, int myTurn) {
        this.totalWaitingCnt = totalWaitingCnt;
        this.myTurn = myTurn;
    }
}
