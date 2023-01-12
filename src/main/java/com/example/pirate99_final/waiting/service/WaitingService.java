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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class WaitingService {

    private final WaitingRepository waitingRepository;
    private final StoreStatusRepository storeStatusRepository;
    private final UserRepository userRepository;

    private final StoreRepository storeRepository;


    @Transactional
    public MsgResponseDto createWaiter(Long storeId, WaitingRequestDto requestDto) {

        Store store = storeRepository.findById(storeId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        StoreStatus storeStatus = storeStatusRepository.findByStore(store);


        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(

                () -> new IllegalArgumentException("유저를 찾을 수 없습니다.")
        );
        Waiting waiting = waitingRepository.save(new Waiting(user, storeStatus));

        storeStatus.update_waitingCnt(storeStatus.getWaitingCnt() + 1);

        return new MsgResponseDto(SuccessCode.CREATE_WAITING);
    }

    @Transactional
    public List<WaitingResponseDto> getListWaiters(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        List<Waiting> waitingList = waitingRepository.findAllByWaitingStatusOrWaitingStatus(0,1);
        List<WaitingResponseDto> waitingResponseDto = new ArrayList<>();

        for (Waiting waiting : waitingList) {
            waitingResponseDto.add(new WaitingResponseDto(waiting));
        }
        return waitingResponseDto;
    }

    @Transactional
    public WaitingResponseDto getWaiter(Long storeId, Long waitingId) {

        Store store = storeRepository.findById(storeId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        Waiting waiting = waitingRepository.findByWaitingId(waitingId);
        return new WaitingResponseDto(waiting);
    }

    @Transactional
    public MsgResponseDto deleteWaiter(Long storeId, Long waitingId) {
        Store store = storeRepository.findById(storeId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        Waiting waiting = waitingRepository.findByWaitingId(waitingId);

        waiting.update(3);
        return new MsgResponseDto(SuccessCode.DELETE_WAITING);
    }
}
