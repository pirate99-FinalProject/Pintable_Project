package com.example.pirate99_final.waiting.dto;

import com.example.pirate99_final.waiting.entity.Waiting;
import lombok.Getter;

@Getter
public class EnterStatusResponseDto {
    private Long waitingId;
    private String waitingStatus;

    private String username;

    public EnterStatusResponseDto(Waiting waiting, String waitingStatus) {
        this.waitingId = waiting.getWaitingId();
        this.waitingStatus = waitingStatus;
        this.username = waiting.getUser().getUsername();
    }
}