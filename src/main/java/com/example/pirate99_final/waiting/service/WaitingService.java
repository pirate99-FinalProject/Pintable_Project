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
import com.example.pirate99_final.waiting.dto.EnterStatusResponseDto;
import com.example.pirate99_final.waiting.dto.MyTurnResponseDto;
import com.example.pirate99_final.waiting.dto.WaitingRequestDto;
import com.example.pirate99_final.waiting.dto.WaitingResponseDto;
import com.example.pirate99_final.waiting.entity.Waiting;
import com.example.pirate99_final.waiting.repository.WaitingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class WaitingService {

    private final WaitingRepository waitingRepository;
    private final StoreStatusRepository storeStatusRepository;
    private final UserRepository userRepository;

    private final StoreRepository storeRepository;

    private final RedissonClient redissonClient;


    public MsgResponseDto createWaiter(Long storeId, WaitingRequestDto requestDto) {                                    // 대기자 등록 시스템
        RLock lock = redissonClient.getLock("key 이름");

        try {
            boolean isLocked = lock.tryLock(60000, 60000, TimeUnit.MILLISECONDS);

            if (isLocked) {
                try {
                    // 스토어 찾기 (storeId)
                    Store store = storeRepository.findById(storeId).orElseThrow(() ->
                            new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
                    );
                    // 스토어 스테이터스 찾기
                    StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeId);

                    // 유저 찾기
                    User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(

                            () -> new IllegalArgumentException("유저를 찾을 수 없습니다.")
                    );

                    // 잔여 좌석이 있을 때는, waitingRepository에 쌓기
                    if(storeStatus.getAvailableTableCnt() > 0 ){
                        waitingRepository.save(new Waiting(user, storeStatus, 1));
                        storeStatus.update_waitingCnt(storeStatus.getWaitingCnt() + 1, storeStatus.getAvailableTableCnt() - 1);
                    }
                    else{
                        // 대기열 중 상태값이 '대기중', '입장가능'인 사람들만 카운팅하기위해 구별해서 리스트에 담음
                        Optional<Waiting> alreadyQueue = waitingRepository.alreadyQueue(0, 1, user.getUserId(), storeStatus.getStoreStatusId());

                        // 종업원이 설정한 대기인원 제한설정 값 보다 현재 대기인원이 적은 경우만 대기자 등록이 가능하게끔 설정
                        if (storeStatus.getWaitingCnt() < storeStatus.getLimitWaitingCnt()) {
                            if (alreadyQueue.isEmpty()) {
                                // 웨이팅(대기자) 등록
                                Waiting waiting = waitingRepository.save(new Waiting(user, storeStatus, 0));
                            } else {
                                return new MsgResponseDto(ErrorCode.ALREADY_IN_QUEUE);
                            }
                        } else {
                            return new MsgResponseDto(ErrorCode.LIMIT_QUEUE_EXCEEDED);
                        }

                        storeStatus.update_waitingCnt(storeStatus.getWaitingCnt() + 1, storeStatus.getAvailableTableCnt());
                    }

                    // 해당 점포 웨이팅 현황 수에 업데이트
                    storeStatusRepository.save(storeStatus);

                    return new MsgResponseDto(SuccessCode.CREATE_WAITING);
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    @Transactional
    public MyTurnResponseDto getMyTurn(Long storeId, WaitingRequestDto requestDto) {                                    // 대기 인원 중 자신의 차례 조회
        // 이용자가 자신의 차례를 조회할 때 쓰는 'myTurn'과 해당 점포 총 대기인원 수 'totalWaitingCnt' 를 선언한다.
        int myTurn = 0;
        int totalWaitingCnt = 0;
        // 스토어 찾기 (storeId)
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );
        // 스토어 스테이터스 찾기
        StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeId);
        // 대기열 중 상태값이 '대기중', '입장가능'인 사람들만 카운팅하기위해 구별해서 리스트에 담음
//        List<Waiting> waitingList = waitingRepository.findAllByStoreStatusAndWaitingStatusOrWaitingStatusOrderByWaitingIdAsc(storeStatus, 0, 1);
        List<Waiting> waitingList = waitingRepository.waitingList(0, 1, storeStatus.getStoreStatusId());
        // 유저 찾기
        User getUser = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        // 총대기 인원수 = 위에서 담은 웨이팅 리스트의 사이즈
        totalWaitingCnt = waitingList.size();


        for (int i = 0; i < waitingList.size(); i++) {
            // 총 대기인원 수 중에 자신의 차례를 찾는 로직 'waitingList' 중 I번째 순서가 자신의 차례임을 나타냄
            if (waitingList.get(i).getUser().equals(getUser)) {
                myTurn = i + 1;
            }
        }
        return new MyTurnResponseDto(totalWaitingCnt, myTurn);
    }


    @Transactional
    public WaitingResponseDto getWaiter(Long storeId, Long waitingId) {                                                 // 대기인원 중 특정 사용자의 정보 불러오기

        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeId);

        Waiting waiting = waitingRepository.findByWaitingId(waitingId);
        return new WaitingResponseDto(waiting);
    }

    @Transactional
    public MsgResponseDto deleteWaiter(Long storeId, Long waitingId) {                                                  // 대기인원 중 대기취소 등의 사유로 상태값을 변경하는 작업
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeId);

        Waiting waiting = waitingRepository.findByWaitingId(waitingId);

        // 대기자 명단에서 상태값을 '3' (대기 취소)으로 변경함
        waiting.update(3);
        return new MsgResponseDto(SuccessCode.DELETE_WAITING);
    }

    public List<EnterStatusResponseDto> getEnterStatus(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        StoreStatus storeStatus = storeStatusRepository.findByStore(store);


        // 대기열 중 상태값이 '대기중', '입장가능'인 사람들만 카운팅하기위해 구별해서 리스트에 담음
        List<Waiting> waitingList = waitingRepository.findAllByStoreStatusOrderByWaitingIdAsc(storeStatus);

        List<EnterStatusResponseDto> waitingResponseDto = new ArrayList<>();

        // 위에서 만든 리스트에 값을 담는 작업
        for (Waiting waiting : waitingList) {
            String waitingStatus = "";
            if(waiting.getWaitingStatus() == 0){
                waitingStatus = "대기";
            }
            else if(waiting.getWaitingStatus() == 1){
                waitingStatus = "입장 가능";
            }
            else if(waiting.getWaitingStatus() == 2){
                waitingStatus = "입장 완료";
            }
            else{
                waitingStatus = "대기 취소";
            }
            waitingResponseDto.add(new EnterStatusResponseDto(waiting,waitingStatus));
        }
        return waitingResponseDto;
    }
}
