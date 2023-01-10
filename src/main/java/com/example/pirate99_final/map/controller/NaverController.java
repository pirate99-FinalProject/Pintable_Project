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

    /*
	  기능 : 메인 페이지
      작성자 : 김규리
      작성일자 : 22.1.10
    */
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("Lat", 37.554666401198936);                                           // 1. 서울역 위도 경도 위치 전달
        model.addAttribute("Lng", 126.97141877645352);
        return "index";
    }

    /*
	  기능 : 현재 페이지
      작성자 : 김규리
      작성일자 : 22.1.10
    */
    @GetMapping("/api/currentLocation")
    public String currentLocation(Model model, @RequestParam String latitude, @RequestParam String longitude,
                                  @RequestParam String storeName) {
        naverService.searchCurrentMap(model, latitude, longitude, storeName);                                            // 1. 위도, 경도, 가게이름 전달
        return "index";
    }

    // 지도 검색
    @GetMapping("/api/searchLocation")
    public String searchMap(Model model, @RequestParam String storeName) {
        naverService.searchMap(model, storeName);                                                                        // 1. 검색한 위도, 경도, 가게이름 전달
        return "index";
    }
}