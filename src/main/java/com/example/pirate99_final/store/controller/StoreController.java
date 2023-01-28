package com.example.pirate99_final.store.controller;


import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.review.dto.ReviewRequestDto;
import com.example.pirate99_final.store.dto.*;
import com.example.pirate99_final.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {
    // connect to StoreService
    private final StoreService storeService;

    // DB save
    @PostMapping("/storeStatus")
    public MsgResponseDto createStore(@RequestBody StoreRequestDto requestDto) {
        return storeService.createStore(requestDto);
    }

    // DB select all
    @GetMapping("/storeStatus")
    public List<StoreStatusResponseDto> getStores() {
        return storeService.getStores();
    }

    // DB select one
    @GetMapping("/storeStatus/{storeId}")
    public StoreStatusResponseDto getStore(@PathVariable long storeId) {
        return storeService.getStore(storeId);
    }

    // DB delete
    @DeleteMapping("/storeStatus/{storeId}")
    public MsgResponseDto deletesStore(@PathVariable Long storeId) {
        return storeService.deleteStore(storeId);
    }

    @PutMapping("/storeStatus/enter/{storeId}")
    public MsgResponseDto enterStore(@PathVariable Long storeId) {
        return storeService.enterStore(storeId);
    }

    @PutMapping("/storeStatus/leave/{storeId}/{people}")
    public MsgResponseDto leaveStore(@PathVariable Long storeId, @PathVariable int people) {
        return storeService.leaveStore(storeId, people);
    }

    @PutMapping("/storeStatus/call/{storeId}")
    public MsgResponseDto callpeople(@PathVariable Long storeId, @RequestBody ConfirmRequestDto requestDto) {
        return storeService.callpeople(storeId, requestDto);
    }

    @PutMapping("/storeStatus/confirmEnter/{storeId}")
    public MsgResponseDto confirmStore(@PathVariable Long storeId, @RequestBody ConfirmRequestDto requestDto) {
        return storeService.confirmStore(storeId, requestDto);
    }

    @PutMapping("/storeStatus/limitWaitingCnt/{storeId}")
    public MsgResponseDto limitWaitingCnt(@PathVariable Long storeId, @RequestBody LimitWaitingCntRequestDto requestDto) {
        return storeService.limitWaitingCnt(storeId, requestDto);
    }


    @GetMapping("/storeAdmin/{storeId}")
    public StoreResponseDto getStoreAdminInfo(@PathVariable Long storeId) {
        return storeService.getStoreAdminInfo(storeId);
    }
}
