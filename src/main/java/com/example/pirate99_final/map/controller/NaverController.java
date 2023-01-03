package com.example.pirate99_final.map.controller;

import com.example.pirate99_final.map.service.NaverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class NaverController {
    private final NaverService naverService;

    @GetMapping("/")
    public String naverMap(Model model, @RequestParam String storeName) {
        // RequestParam을 통해 url에 검색어를 담아서 보낸다.
        naverService.naverMap(model, storeName);
        return "index";
    }
}
