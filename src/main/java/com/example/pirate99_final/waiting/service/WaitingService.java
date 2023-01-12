package com.example.pirate99_final.waiting.service;

import com.example.pirate99_final.global.MsgResponseDto;

import com.example.pirate99_final.global.exception.CustomException;
import com.example.pirate99_final.global.exception.ErrorCode;
import com.example.pirate99_final.global.exception.SuccessCode;
import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.store.entity.StoreStatus;
import com.example.pirate99_final.store.repository.StoreRepository;
import com.example.pirate99_final.store.repository.StoreStatusRepository;
import com.example.pirate99_final.user.entity.User;
import com.example.pirate99_final.user.repository.UserRepository;
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

    private final StoreStatusRepository storeStatusRepository;
    private final UserRepository userRepository;


    @Transactional
    public MsgResponseDto createWaiter(Long storeStatusId, WaitingRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow();
        StoreStatus storeStatus = storeStatusRepository.findById(storeStatusId)
                .orElseThrow();
        Waiting waiting = waitingRepository.save(new Waiting(user, storeStatus, requestDto));
        if(waiting.getUser() == null) {
            throw new CustomException(ErrorCode.WAITING_POST_ERROR);
        }
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
