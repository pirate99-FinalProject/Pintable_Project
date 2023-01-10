package com.example.pirate99_final.waiting.service;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.global.exception.SuccessCode;
import com.example.pirate99_final.waiting.dto.WaitingRequestDto;
import com.example.pirate99_final.waiting.entity.Waiting;
import com.example.pirate99_final.waiting.repository.WaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.LinkedList;


@Service
@RequiredArgsConstructor
public class WaitingService {

    private final WaitingRepository waitingRepository;



    @Transactional
    public MsgResponseDto createWaiter(WaitingRequestDto requestDto) {

        Waiting waiting = waitingRepository.save(new Waiting(requestDto));

        return new MsgResponseDto(SuccessCode.CREATE_WAITING);
    }
}
