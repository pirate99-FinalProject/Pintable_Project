package com.example.pirate99_final.waiting.controller;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.waiting.dto.WaitingRequestDto;
import com.example.pirate99_final.waiting.dto.WaitingResponseDto;
import com.example.pirate99_final.waiting.service.WaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WaitingController {

    private final WaitingService waitingService;

    @PostMapping(value = "/waiting")
    public MsgResponseDto createWaiter(WaitingRequestDto request) {
        return waitingService.createWaiter(request);
    }

    @GetMapping("/waitingList")
    public List<WaitingResponseDto> getListWaiters() {
        return waitingService.getListWaiters();
    }

    @GetMapping("/waitingList/{waitingId}")
    public WaitingResponseDto getWaiter(@PathVariable Long waitingId) {
        return waitingService.getWaiter(waitingId);
    }

    @DeleteMapping("waitingList/{waitingId}")
    public MsgResponseDto deleteWaiter(@PathVariable Long waitingId) {
        return waitingService.deleteWaiter(waitingId);
    }
}
