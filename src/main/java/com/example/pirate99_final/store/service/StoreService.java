package com.example.pirate99_final.store.service;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.global.exception.CustomException;
import com.example.pirate99_final.global.exception.ErrorCode;
import com.example.pirate99_final.global.exception.SuccessCode;
import com.example.pirate99_final.store.config.SearchCondition;
import com.example.pirate99_final.store.dto.ConfirmRequestDto;
import com.example.pirate99_final.store.dto.StoreRequestDto;
import com.example.pirate99_final.store.dto.StoreStatusResponseDto;
import com.example.pirate99_final.store.dto.*;
import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.store.entity.StoreStatus;
import com.example.pirate99_final.store.repository.StoreRepositoryImpl;
import com.example.pirate99_final.store.repository.StoreRepository;
import com.example.pirate99_final.store.repository.StoreStatusRepository;
import com.example.pirate99_final.user.entity.User;
import com.example.pirate99_final.user.repository.UserRepository;
import com.example.pirate99_final.waiting.entity.Waiting;
import com.example.pirate99_final.waiting.repository.WaitingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static com.example.pirate99_final.global.exception.SuccessCode.*;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;                      // store repo connect
    private final StoreStatusRepository storeStatusRepository;          // storeStatus repo connect

    private final WaitingRepository waitingRepository;                  // waiting repo connect

    private final UserRepository userRepository;                        // user repo connect

    private final StoreRepositoryImpl naverRepositoryImpl;                                                              // query Dsl Repository 의존성 주입
    private final JavaMailSender emailSender;                                 // email sender

    // Store Create function
    public MsgResponseDto createStore(StoreRequestDto requestDto){
        // 1. create store and storeStatus Object, insert DB
        Store store = new Store(requestDto);                                                                // DTO -> Entity
        StoreStatus storestatus = new StoreStatus(store);

        // 2. DB save
        storeRepository.save(store);                                                                        // DB Save
        storeStatusRepository.save(storestatus);

        return new MsgResponseDto(CREATE_STORE);
    }

    // Get memos from DB (all)

    public List<StoreStatusResponseDto> getStores() {
        // 1. Select All Memo
        List<StoreStatus> ListStoreStatus = storeStatusRepository.findAllBy();                          // Select All

        List<StoreStatusResponseDto> ListResponseDto = new ArrayList<>();

        for(StoreStatus storeStatus : ListStoreStatus){
            ListResponseDto.add(new StoreStatusResponseDto(storeStatus));
        }
        return ListResponseDto;
    }

    // Get store from DB (one)
    public StoreStatusResponseDto getStore(long storeId){
        Store store = storeRepository.findById(storeId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        StoreStatusResponseDto responseDto = new StoreStatusResponseDto(storeStatus);

        return responseDto;
    }

    // DB delete function (data delete)

    public MsgResponseDto deleteStore(Long storeId) {

        Store store  = storeRepository.findById(storeId).orElseThrow(                                             // find store
                () -> new CustomException(ErrorCode.NOT_FOUND_ID_ERROR)
        );

        storeStatusRepository.deleteByStore(store);                                     // 상점 상태 테이블 삭제
        storeRepository.deleteById(storeId);                                                          // 해당 상점 삭제

        return  new MsgResponseDto(DELETE_STORE);
    }

    @Transactional
    public MsgResponseDto enterStore(Long storeId, int people) {
        int availableCnt   =   0;                                                       // 이용 가능 좌석

        // 1. find store
        Store store = storeRepository.findById(storeId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        // 2. storeStatus check
        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        // 3. counting availableCnt
        if((storeStatus.getAvailableTableCnt() - people) > 0){
            availableCnt = storeStatus.getAvailableTableCnt() - people;
        }
        else if(storeStatus.getAvailableTableCnt() == 0){
            throw new CustomException(ErrorCode.NOT_ENOUGH_TABLE);
        }
        else if((storeStatus.getAvailableTableCnt() - people) < 0){
            String message  = Integer.toString(Math.abs(storeStatus.getAvailableTableCnt() - people));
            availableCnt    = 0;

            storeStatus.update(availableCnt);

            return new MsgResponseDto(HttpStatus.OK.value(),"빈 자석이 없습니다. " + message + "분은 예약 등록 부탁드립니다.");
        }

        // 4. update storeStatus
        storeStatus.update(availableCnt);

        return new MsgResponseDto(SuccessCode.CONFIRM_ENTER);
    }

    // Leave people from store
    @Transactional
    public MsgResponseDto leaveStore(Long storeId, int people) {
        int availableCnt   =   0;                                                       // 이용 가능 좌석

        // 1. find store
        Store store = storeRepository.findById(storeId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        // 2. storeStatus check
        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        availableCnt = storeStatus.getAvailableTableCnt() + people;

        // Leaving people check
        storeStatus.update(availableCnt);

        return new MsgResponseDto(SuccessCode.CONFIRM_LEAVE);
    }

    @Transactional
    public MsgResponseDto confirmStore(Long storeId, ConfirmRequestDto requestDto) {
        // 1. find store
        Store store = storeRepository.findById(storeId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        // 2. storeStatus check
        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        // 3. User find
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_USER_ERROR)
        );

        Waiting waiting = waitingRepository.findByStoreStatusAndUser(storeStatus, user);

        if(requestDto.getWaitingStatus() == 1){
            waiting.update(2);
            int waitingCnt = storeStatus.getWaitingCnt() - 1;
            int availableCnt = storeStatus.getAvailableTableCnt();

            if(availableCnt > 0){
                availableCnt = availableCnt - 1;
            }

            storeStatus.update_waitingCnt(waitingCnt, availableCnt);
        }
        else if(requestDto.getWaitingStatus() == 3){
            waiting.update(3);
            int waitingCnt = storeStatus.getWaitingCnt() - 1;
            int availableCnt = storeStatus.getAvailableTableCnt();

            storeStatus.update_waitingCnt(waitingCnt, availableCnt);
        }

        return new MsgResponseDto(SuccessCode.CONFIRM_ENTER);
    }

    // call people
    @Transactional
    public MsgResponseDto callpeople(Long storeId, ConfirmRequestDto requestDto) {
        // 1. find store
        Store store = storeRepository.findById(storeId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        // 2. storeStatus check
        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        // 3. User find
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_USER_ERROR)
        );

        Waiting waiting = waitingRepository.findByStoreStatusAndUser(storeStatus, user);
        waiting.update(1);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("developjisung@gmail.com");
        message.setTo(user.getAddress());
        message.setSubject(store.getStoreName() + " 대기 호출");
        message.setText(store.getStoreName() + "에 대기해주신 고객님 감사합니다. 입장 부탁드립니다.");
        emailSender.send(message);

        return new MsgResponseDto(SuccessCode.CALL_PEOPLE);
    }

    // 기능 : 현재 위치에서 검색 기능
    public void searchCurrentMap(Model model, String latitude, String longitude, String storeName) {

        List<Store> naverList = storeRepository.searchCurrent(latitude, longitude, storeName);                           // 1. 입력받은 위도, 경도, 가게 이름으로 DB에 검사한다.
        model.addAttribute("searchList", naverList);                                                         // 2. index.html에 검색한 결과 전달
    }
    // 기능 : 지도 검색 기능
    public void searchMap(Model model, String storeName) {
        String storeNameTrim = storeName.replaceAll(" ", "");                                            // 1. 검색 시 키워드 검색을 위한 문자 치환(" ", "")
        List<Store> naverList = storeRepository.findByStoreNameContaining(storeNameTrim);                                // 2. %Like% 로 장소 검색
        model.addAttribute("searchList", naverList);                                                         // 3. index.html에 검색한 결과 전달
    }

    public void DynamicSQL(Model model, SearchCondition condition, String select) {
        List<Store> DynamicSQL = naverRepositoryImpl.DynamicSQL(condition, select);
        model.addAttribute("searchList", DynamicSQL);
    }
}
