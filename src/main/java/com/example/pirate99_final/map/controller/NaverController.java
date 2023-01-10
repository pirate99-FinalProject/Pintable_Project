package com.example.pirate99_final.map.controller;

import com.example.pirate99_final.map.service.NaverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class NaverController {
    private final NaverService naverService;

    // 메인 페이지
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("Lat0", 37.554666401198936);
        model.addAttribute("Lng0", 126.97141877645352);
        return "index";
    }

    // 현재 위치
    @GetMapping("/api/currentLocation")
    public String currentLocation(Model model, String latitude, String longitude) {
        model.addAttribute("Lat0", latitude);
        model.addAttribute("Lng0", longitude);
        return "index";
    }

    // 지도 검색
    @GetMapping("/api/searchLocation")
    public String searchMap(Model model, @RequestParam String storeName) {
        // RequestParam을 통해 url에 검색어를 담아서 보낸다.
        naverService.searchMap(model, storeName);
        return "index";
    }
}