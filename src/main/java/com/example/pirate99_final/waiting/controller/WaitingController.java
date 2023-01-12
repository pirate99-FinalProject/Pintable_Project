package com.example.pirate99_final.waiting.controller;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.waiting.dto.MyTurnResponseDto;
import com.example.pirate99_final.waiting.dto.WaitingRequestDto;
import com.example.pirate99_final.waiting.dto.WaitingResponseDto;
import com.example.pirate99_final.waiting.service.WaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WaitingController {

    private final WaitingService waitingService;

    @PostMapping(value = "/waitingList/{storeId}")
    public MsgResponseDto createWaiter(@PathVariable Long storeId, @RequestBody WaitingRequestDto request) {
        return waitingService.createWaiter(storeId, request);
    }

    @GetMapping("/waitingList/myTurn/{storeId}")
    public MyTurnResponseDto getMyTurn(@PathVariable Long storeId, @RequestBody WaitingRequestDto requestDto) {

        return waitingService.getMyTurn(storeId, requestDto);
    }

    @GetMapping("/waitingList/{storeId}")
    public List<WaitingResponseDto> getListWaiters(@PathVariable Long storeId) {

        return waitingService.getListWaiters(storeId);
    }

    @GetMapping("/waitingList/{storeId}/{waitingId}")
    public WaitingResponseDto getWaiter(@PathVariable Long storeId, @PathVariable Long waitingId) {

        return waitingService.getWaiter(storeId, waitingId);
    }

    @PutMapping("/waitingList/{storeId}/{waitingId}")
    public MsgResponseDto deleteWaiter(@PathVariable Long storeId, @PathVariable Long waitingId) {
        return waitingService.deleteWaiter(storeId, waitingId);
    }
}
