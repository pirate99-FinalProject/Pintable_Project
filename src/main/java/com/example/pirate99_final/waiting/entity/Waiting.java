package com.example.pirate99_final.waiting.entity;

import com.example.pirate99_final.waiting.dto.WaitingRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Waiting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long waitingId;

    @Column(nullable = false)
    private String waitingStatus;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long storeStatusId;

    public Waiting(WaitingRequestDto requestDto) {
    }
}
