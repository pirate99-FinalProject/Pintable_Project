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
    // 가게명 일치 여부
    @GetMapping("/api/search")
    public String findByOneStoreName(Model model, @RequestParam String storeName) {
        // RequestParam을 통해 url에 검색어를 담아서 보낸다.
        naverService.findByOneStoreName(model, storeName);
        return "index";
    }
    // 가게명 일부 포함
    @GetMapping("/api/search1")
    public String findByStoreNameInclude(Model model, @RequestParam String storeNameInclude) {
        // RequestParam을 통해 url에 검색어를 담아서 보낸다.
        naverService.findByStoreNameInclude(model, storeNameInclude);
        return "index";
    }
    // 도로명주소 포함 여부 (도로명주소의 일부 일치)
    @GetMapping("/api/search2")
    public String findByRoadAddressInclude(Model model, @RequestParam String roadNameAddress) {
        // RequestParam을 통해 url에 검색어를 담아서 보낸다.
        naverService.findByRoadAddressInclude(model, roadNameAddress);
        return "index";
    }
    // 업종별 분류
    @GetMapping("/api/search3")
    public String findByBusiness(Model model, @RequestParam String typeOfBusiness) {
        // RequestParam을 통해 url에 검색어를 담아서 보낸다.
        naverService.findByBusiness(model, typeOfBusiness);
        return "index";
    }
    // 평점 낮은순 ASC
    @GetMapping("/api/search4")
    public String OrderByStarScoreASC(Model model) {
        naverService.OrderByStarScoreASC(model);
        return "index";
    }
    // 평점 높은순 DESC
    @GetMapping("/api/search5")
    public String OrderByStarScoreDESC(Model model) {
        naverService.OrderByStarScoreDESC(model);
        return "index";
    }
    // 리뷰 낮은순 ASC
    @GetMapping("/api/search6")
    public String OrderByReviewASC(Model model) {
        naverService.OrderByReviewASC(model);
        return "index";
    }
    // 리뷰 높은순 DESC
    @GetMapping("/api/search7")
    public String OrderByReviewDESC(Model model) {
        naverService.OrderByReviewDESC(model);
        return "index";
    }
    // 평점 4점 이상
    @GetMapping("/api/search8")
    public String BetweenStarScoreHigh(Model model) {
        naverService.BetweenStarScoreHigh(model);
        return "index";
    }
    // 평점 2점 이하
    @GetMapping("/api/search9")
    public String BetweenStarScoreLow(Model model) {
        naverService.BetweenStarScoreLow(model);
        return "index";
    }
    // 리뷰 1000개 이상
    @GetMapping("/api/search10")
    public String BetweenReviewHigh(Model model) {
        naverService.BetweenReviewHigh(model);
        return "index";
    }
    // 리뷰 10개 이하
    @GetMapping("/api/search11")
    public String BetweenReviewLow(Model model) {
        naverService.BetweenReviewLow(model);
        return "index";
    }
}
