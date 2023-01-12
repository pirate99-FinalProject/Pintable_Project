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

    /*
      기능 : 하나의 가게를 찾는 페이지
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    @GetMapping("/api/search/storeName")
    public String findByOneStoreName(Model model, @RequestParam String storeName) {
        naverService.findByOneStoreName(model, storeName);
        return "index";
    }

    /*
      기능 : 검색어를 포함한 가게를 찾는 페이지
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    @GetMapping("/api/search/storeNameInclude")
    public String findByStoreNameInclude(Model model, @RequestParam String storeNameInclude) {
        naverService.findByStoreNameInclude(model, storeNameInclude);
        return "index";
    }

    /*
      기능 : 도로명 주소를 포함한 가게를 찾는 페이지
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    @GetMapping("/api/search/roadAddressInclude")
    public String findByRoadAddressInclude(Model model, @RequestParam String roadNameAddress) {
        naverService.findByRoadAddressInclude(model, roadNameAddress);
        return "index";
    }

    /*
      기능 : 검색한 업종인 가게를 찾는 페이지
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    @GetMapping("/api/search/Business")
    public String findByBusiness(Model model, @RequestParam String typeOfBusiness) {
        naverService.findByBusiness(model, typeOfBusiness);
        return "index";
    }

    /*
      기능 : 평점이 낮은 순서로 정렬되어 상위10개만 보여주는 페이지
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    @GetMapping("/api/search/StarScoreASC")
    public String OrderByStarScoreASC(Model model) {
        naverService.OrderByStarScoreASC(model);
        return "index";
    }

    /*
      기능 : 평점이 높은 순서로 정렬되어 상위10개만 보여주는 페이지
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    @GetMapping("/api/search/StarScoreDESC")
    public String OrderByStarScoreDESC(Model model) {
        naverService.OrderByStarScoreDESC(model);
        return "index";
    }

    /*
      기능 : 리뷰가 적은 순서로 정렬되어 상위 10개만 보여주는 페이지
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    @GetMapping("/api/search/ReviewASC")
    public String OrderByReviewASC(Model model) {
        naverService.OrderByReviewASC(model);
        return "index";
    }

    /*
      기능 : 리뷰가 많은 순서로 정렬되어 상위 10개만 보여주는 페이지
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    @GetMapping("/api/search/ReviewDESC")
    public String OrderByReviewDESC(Model model) {
        naverService.OrderByReviewDESC(model);
        return "index";
    }

    /*
      기능 : 평점이 높은 가게를 찾는 페이지 4점이상
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    @GetMapping("/api/search/StarScoreHigh")
    public String BetweenStarScoreHigh(Model model) {
        naverService.BetweenStarScoreHigh(model);
        return "index";
    }

    /*
      기능 : 평점이 낮은 가게를 찾는 페이지 2점이하
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    @GetMapping("/api/search/StarScoreLow")
    public String BetweenStarScoreLow(Model model) {
        naverService.BetweenStarScoreLow(model);
        return "index";
    }

    /*
      기능 : 리뷰가 1000개 이상인 가게를 찾는 페이지
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    @GetMapping("/api/search/ReviewHigh")
    public String BetweenReviewHigh(Model model) {
        naverService.BetweenReviewHigh(model);
        return "index";
    }

    /*
      기능 : 리뷰가 10개 이인 가게를 찾는 페이지
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    @GetMapping("/api/search/ReviewLow")
    public String BetweenReviewLow(Model model) {
        naverService.BetweenReviewLow(model);
        return "index";
    }
}

