package com.example.pirate99_final.waiting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WaitingStatus {

    WAITING(0),                                                             // 대기 중
    ADMISSION_ALLOWED(1),                                                   // 입장 가능
    ADMISSION_COMPLETE(2),                                                  // 입장 완료
    CANCELLATIONS(3),                                                       // 대기 취소
    LEAVING(4);                                                             // 손님 퇴장

    private final int waitingStatus;
}
