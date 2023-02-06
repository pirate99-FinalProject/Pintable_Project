package com.example.pirate99_final.waiting.controller;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.waiting.dto.EnterStatusResponseDto;
import com.example.pirate99_final.waiting.dto.MyTurnResponseDto;
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

    @PostMapping(value = "/waitingList/{storeId}")
    public MsgResponseDto createWaiter(@PathVariable Long storeId, @RequestBody WaitingRequestDto request) {
        return waitingService.createWaiter(storeId, request);
    }

    @PostMapping("/waitingList/myTurn/{storeId}")
    public MyTurnResponseDto getMyTurn(@PathVariable Long storeId, @RequestBody WaitingRequestDto requestDto) {

        return waitingService.getMyTurn(storeId, requestDto);
    }

    @GetMapping("/waitingList/{waitingId}")
    public WaitingResponseDto getWaiter(@PathVariable Long waitingId) {

        return waitingService.getWaiter(waitingId);
    }

    @PutMapping("/waitingList/{waitingId}")
    public MsgResponseDto deleteWaiter(@PathVariable Long waitingId) {
        return waitingService.deleteWaiter(waitingId);
    }

    // DB select all (User Info)
    @GetMapping("/enterStatus/{storeId}")
    public List<EnterStatusResponseDto> getEnterStatus(@PathVariable Long storeId){
        return waitingService.getEnterStatus(storeId);}
}
