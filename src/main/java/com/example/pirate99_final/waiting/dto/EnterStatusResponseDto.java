package com.example.pirate99_final.waiting.dto;

import com.example.pirate99_final.user.entity.User;
import com.example.pirate99_final.waiting.entity.Waiting;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EnterStatusResponseDto {
    private Long waitingId;
    private int waitingStatus;

    private String username;

    public EnterStatusResponseDto(Waiting waiting) {
        this.waitingId = waiting.getWaitingId();
        this.waitingStatus = waiting.getWaitingStatus();
        this.username = waiting.getUser().getUsername();
    }
}