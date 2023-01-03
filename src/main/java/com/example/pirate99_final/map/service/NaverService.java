package com.example.pirate99_final.map.service;

import com.example.pirate99_final.map.entity.Naver;
import com.example.pirate99_final.map.repository.NaverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class NaverService {
    private final NaverRepository naverRepository;

    public void naverMap(Model model, String storeName) {
        // 1. DB에서 가게 이름을 조회 한다.
        Naver naver = naverRepository.findByStoreName(storeName).orElseThrow(
                () -> new RuntimeException("가게가 존재하지 않습니다.")
        );
        // 2. 조회한 가게이름의 위도 경도 값을 index.html로 보낸다.
        model.addAttribute("Lat", naver.getLat());
        model.addAttribute("lng", naver.getLng());
    }
}