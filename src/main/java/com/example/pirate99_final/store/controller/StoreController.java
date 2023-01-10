package com.example.pirate99_final.store.controller;


import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.store.dto.StoreRequestDto;
import com.example.pirate99_final.store.dto.StoreResponseDto;
import com.example.pirate99_final.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public List<StoreResponseDto> getStores(){return storeService.getStores();}

    // DB select one
    @GetMapping("/storeStatus/{id}")
    public StoreResponseDto getStore(@PathVariable long id){return storeService.getStore(id);}

    // DB delete
    @DeleteMapping("/storeStatus/{id}")
    public MsgResponseDto deletesStore(@PathVariable Long id) {
        return storeService.deleteStore(id);
    }
}
