package com.example.pirate99_final.waiting.dto;

public class WaitingMsgResponseDto extends MyTurnResponseDto{

    private int statusCode;
    private String msg;

    public WaitingMsgResponseDto(int totalWaitingCnt, int myTurn) {
        super(totalWaitingCnt, myTurn);
    }
}
