package com.example.pirate99_final.waiting.dto;

import com.example.pirate99_final.waiting.entity.Waiting;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WaitingResponseDto {

    private Long waitingId;

    private int waitingStatus;

    private Long userId;


    public WaitingResponseDto(Waiting waiting) {
        this.waitingId = waiting.getWaitingId();
        this.waitingStatus = waiting.getWaitingStatus();
        this.userId = waiting.getUserId();
    }
}
