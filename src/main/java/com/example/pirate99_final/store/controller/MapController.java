package com.example.pirate99_final.store.controller;

import com.example.pirate99_final.store.config.SearchCondition;
import com.example.pirate99_final.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class MapController {
    private final StoreService storeService;

    // 기능 : 메인 페이지
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("Lat", 37.554666401198936);                                          // 1. 서울역 위도 경도 위치 전달
        model.addAttribute("Lng", 126.97141877645352);
        return "index";
    }

    // 기능 : 현재 페이지
    @GetMapping("/api/currentLocation")
    public String currentLocation(Model model, @RequestParam String latitude, @RequestParam String longitude,
                                  @RequestParam String storeName) {
        storeService.searchCurrentMap(model, latitude, longitude, storeName);                                           // 1. 위도, 경도, 가게이름 전달
        return "index";
    }

    // ElasticSearch
    @GetMapping("/api/search/{condition}")
    public String ElasticSearch(Model model, HttpServletRequest request, SearchCondition condition) {
        String uri = request.getRequestURI();
        String select = uri.substring(12);
        select = select.replace("/", "");                                           // url 정보 저장

        switch (select) {
            case "StarScoreDESC"  -> storeService.elasticSearchStarScoreDESC(model, condition);     // 별점 높은순
            case "ReviewDESC"     -> storeService.elasticSearchReviewDESC(model, condition);        // 리뷰 많은순
            case "StarScore"      -> storeService.elasticSearchStarScore(model, condition);         // 별점 4점 이상
            case "Review"         -> storeService.elasticSearchReview(model, condition);            // 리뷰 1000개 이상
            default               -> storeService.elasticSearch(model, condition);                  // 검색어로만 검색할때
        }
        return "index";
    }

    @GetMapping("/adminLogin")
    public String adminLogin() {
        return "index-adminLogin";
    }

    @GetMapping("/admin")
    public String admin() {
        return "index-admin";
    }
}