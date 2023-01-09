package com.example.pirate99_final.map.service;

import com.example.pirate99_final.map.entity.Naver;
import com.example.pirate99_final.map.repository.NaverRepository;
import com.example.pirate99_final.map.repository.NaverRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class NaverService {
    private final NaverRepository naverRepository;
    private final NaverRepositoryImpl naverRepositoryImpl;

    public void naverMap(Model model, String storeName) {
        // 1. DB에서 가게 이름을 조회 한다.
        Naver naver = naverRepository.findByStoreName(storeName).orElseThrow(
                () -> new RuntimeException("가게가 존재하지 않습니다.")
        );
        // 2. 조회한 가게이름의 위도 경도 값을 index.html로 보낸다.
        model.addAttribute("Lat", naver.getLat());
        model.addAttribute("Lng", naver.getLng());
//****************************************************************************************************************
//        // 가게명 일치 여부
//        Naver findByOneStoreName = naverRepositoryImpl.findByStoreName(storeName);
//        // 가게명 일부 포함
//        List<Naver> findByStoreNameInclude = naverRepositoryImpl.findByStoreNameInclude(storeName);
//        // 도로명주소 포함 여부 (도로명주소의 일부 일치)
//        List<Naver> findByRoadAddressInclude = naverRepositoryImpl.findByroadAddressInclude(roadNameAddress);
//        // 업종별 분류
//        List<Naver> findByBusiness = naverRepositoryImpl.findByBusiness(typeOfBusiness);
//        // 평점 낮은순 ASC
//        List<Naver> OrderByStarScoreASC = naverRepositoryImpl.OrderByStarScore(starScore);
//        // 평점 높은순 DESC
//        List<Naver> OrderByStarScoreDESC = naverRepositoryImpl.OrderByStarScoreDESC(starScore);
//        // 리뷰 낮은순 ASC
//        List<Naver> OrderByReviewASC = naverRepositoryImpl.OrderByReview(reviewCnt);
//        // 리뷰 높은순 DESC
//        List<Naver> OrderByReviewDESC = naverRepositoryImpl.OrderByReviewDESC(reviewCnt);
//        // 평점 4점 이상
//        List<Naver> BetweenStarScoreHigh = naverRepositoryImpl.BetweenStarScoreHigh(starScore);
//        // 평점 1점 이하
//        List<Naver> BetweenStarScoreLow = naverRepositoryImpl.BetweenStarScoreLow(starScore);
//        // 리뷰 1000개 이상
//        List<Naver> BetweenReviewHigh = naverRepositoryImpl.BetweenReviewHigh(reviewCnt);
//        // 리뷰 10개 이하
//        List<Naver> BetweenReviewLow = naverRepositoryImpl.BetweenReviewLow(reviewCnt);
//****************************************************************************************************************
    }
}