package com.example.pirate99_final.store.controller;


import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.store.dto.StoreRequestDto;
import com.example.pirate99_final.store.dto.StoreStatusResponseDto;
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
    public MsgResponseDto createStore(@RequestBody StoreRequestDto requestDto){
        return storeService.createStore(requestDto);
    }

    // DB select all
    @GetMapping("/storeStatus")
    public List<StoreStatusResponseDto> getStores(){return storeService.getStores();}

    // DB select one
    @GetMapping("/storeStatus/{storeId}")
    public StoreStatusResponseDto getStore(@PathVariable long storeId){return storeService.getStore(storeId);}

    // DB delete
    @DeleteMapping("/storeStatus/{storeId}")
    public MsgResponseDto deletesStore(@PathVariable Long storeId) {
        return storeService.deleteStore(storeId);
    }
}
