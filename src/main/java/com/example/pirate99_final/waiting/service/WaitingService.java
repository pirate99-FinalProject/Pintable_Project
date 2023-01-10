package com.example.pirate99_final.waiting.service;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.global.exception.SuccessCode;
import com.example.pirate99_final.waiting.dto.WaitingRequestDto;
import com.example.pirate99_final.waiting.dto.WaitingResponseDto;
import com.example.pirate99_final.waiting.entity.Waiting;
import com.example.pirate99_final.waiting.repository.WaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WaitingService {

    private final WaitingRepository waitingRepository;



    @Transactional
    public MsgResponseDto createWaiter(WaitingRequestDto requestDto) {

        Waiting waiting = waitingRepository.save(new Waiting(requestDto));

        return new MsgResponseDto(SuccessCode.CREATE_WAITING);
    }

    @Transactional
    public List<WaitingResponseDto> getListWaiters() {
        List<Waiting> waitingList = waitingRepository.findAllByOrderByCreatedAtDesc();
        List<WaitingResponseDto> waitingResponseDto = new ArrayList<>();

        for (Waiting waiting : waitingList) {
            waitingResponseDto.add(new WaitingResponseDto(waiting));
        }
        return waitingResponseDto;
    }

    @Transactional
    public WaitingResponseDto getWaiter(Long waitingId) {
        Waiting waiting = waitingRepository.findByWaitingId(waitingId);
        return new WaitingResponseDto(waiting);
    }

    @Transactional
    public MsgResponseDto deleteWaiter(Long waitingId) {
        Waiting waiting = waitingRepository.findByWaitingId(waitingId);
        waitingRepository.delete(waiting);
        return new MsgResponseDto(SuccessCode.DELETE_WAITING);
    }
}
