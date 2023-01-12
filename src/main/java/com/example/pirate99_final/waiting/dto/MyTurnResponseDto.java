package com.example.pirate99_final.waiting.dto;

import lombok.Getter;

@Getter
public class MyTurnResponseDto {

    private int totalWaitingCnt;

    private int myTurn;

    public MyTurnResponseDto(int totalWaitingCnt, int myTurn) {
        this.totalWaitingCnt = totalWaitingCnt;
        this.myTurn = myTurn;
    }
}
