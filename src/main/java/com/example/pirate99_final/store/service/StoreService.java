package com.example.pirate99_final.store.service;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.global.exception.CustomException;
import com.example.pirate99_final.global.exception.ErrorCode;
import com.example.pirate99_final.review.entity.Review;
import com.example.pirate99_final.store.dto.StoreRequestDto;
import com.example.pirate99_final.store.dto.StoreResponseDto;
import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.pirate99_final.global.exception.SuccessCode.CREATE_STORE;
import static com.example.pirate99_final.global.exception.SuccessCode.DELETE_REVIEW;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;                      // store repo connect

    // Store Create function
    public MsgResponseDto createStore(StoreRequestDto requestDto){
        //1. create store Object and insert DB
        Store store = new Store(requestDto);                  // DTO -> Entity
        storeRepository.save(store);                                                                      // DB Save
        return new MsgResponseDto(CREATE_STORE);                                                               // return Response  Entity -> DTO
    }

    // Get memos from DB (all)
    public List<StoreResponseDto> getStores() {
        // 1. Select All Memo
        List<Store> ListStore = storeRepository.findAllByOrderByIdAtDesc();                          // Select All

        List<StoreResponseDto> ListResponseDto = new ArrayList<>();

        for(Store store : ListStore){
            ListResponseDto.add(new StoreResponseDto(store));
        }
        return ListResponseDto;
    }

    // Get store from DB (one)
    public StoreResponseDto getStore(long id){
        Store store = storeRepository.findById(id).orElseThrow(()->                                        // Select one
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        StoreResponseDto responseDto = new StoreResponseDto(store);

        return responseDto;
    }

    // DB delete function (data delete)

    public MsgResponseDto deleteStore(Long id) {

        Store store  = storeRepository.findById(id).orElseThrow(                                             // find memo
                () -> new CustomException(ErrorCode.NOT_FOUND_ID_ERROR)
        );
        storeRepository.deleteById(id);                                                          // 해당 게시물 삭제

        return  new MsgResponseDto(DELETE_REVIEW);
    }
}
