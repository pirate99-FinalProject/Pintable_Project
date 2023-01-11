package com.example.pirate99_final.waiting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum WaitingStatus {



    WAITING(0),                                                             // 대기 중
    ADMISSION_ALLOWED(1),                                                   // 입장 가능
    ADMISSION_COMPLETE(2),                                                  // 입장 완료
    CANCELLATIONS(3);                                                       // 대기 취소


    private final int waitingStatus;

    public static WaitingStatus valueOfWaitingStatus(int waitingStatus){
        return Arrays.stream(values())
                .filter(value -> value.waitingStatus==(waitingStatus))
                .findAny()
                .orElse(null);
    }
}
