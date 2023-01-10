package com.example.pirate99_final.waiting.entity;

import com.example.pirate99_final.global.entity.TimeStamped;
import com.example.pirate99_final.waiting.dto.WaitingRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Waiting extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long waitingId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long storeStatusId;

    @Column
    private int waitingStatus;

    public Waiting(WaitingRequestDto requestDto) {
    }
}
