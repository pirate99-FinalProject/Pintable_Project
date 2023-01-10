package com.example.pirate99_final.waiting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum WaitingStatus {



    WAITING(0),
    ADMISSION_ALLOWED(1),
    ADMISSION_COMPLETE(2),
    CANCELLATIONS(3);


    private final int waitingStatus;

    public static WaitingStatus valueOfWaitingStatus(int waitingStatus){
        return Arrays.stream(values())
                .filter(value -> value.waitingStatus==(waitingStatus))
                .findAny()
                .orElse(null);
    }
}
