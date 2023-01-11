package com.example.pirate99_final.waiting.controller;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.waiting.dto.WaitingRequestDto;
import com.example.pirate99_final.waiting.service.WaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WaitingController {

    private final WaitingService waitingService;

    @PostMapping(value = "/waiting")
    public MsgResponseDto createWaiting(@PathVariable WaitingRequestDto request) {
        return  waitingService.createWaiter(request);
    }
//
//    @GetMapping("/waitingList")
//    public List<>


}
