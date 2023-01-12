package com.example.pirate99_final.waiting.controller;

import com.example.pirate99_final.global.MsgResponseDto;
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

    @PostMapping(value = "/waitingList/{storeStatusId}")
    public MsgResponseDto createWaiter(@PathVariable Long storeStatusId, @RequestBody WaitingRequestDto request) {
        return waitingService.createWaiter(storeStatusId, request);
    }

    @GetMapping("/waitingList/{storeStatusId}")
    public List<WaitingResponseDto> getListWaiters(@PathVariable Long storeStatusId) {
        return waitingService.getListWaiters(storeStatusId);
    }

    @GetMapping("/waitingList/{storeStatusId}/{waitingId}")
    public WaitingResponseDto getWaiter(@PathVariable Long storeStatusId, @PathVariable Long waitingId) {


        return waitingService.getWaiter(storeStatusId, waitingId);
    }

    @DeleteMapping("/waitingList/{storeStatusId}/{waitingId}")
    public MsgResponseDto deleteWaiter(@PathVariable Long storeStatusId, @PathVariable Long waitingId) {
        return waitingService.deleteWaiter(waitingId);
    }
}
