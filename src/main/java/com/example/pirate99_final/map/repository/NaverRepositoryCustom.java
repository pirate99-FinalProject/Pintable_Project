package com.example.pirate99_final.map.repository;

import com.example.pirate99_final.map.entity.Naver;

import java.util.List;

public interface NaverRepositoryCustom {
    // 가게명 일치 여부
    Naver findByStoreName(String storeName);
    // 가게명 포함 여부 (가게명의 일부 일치)
    List<Naver> findByStoreNameInclude(String storeName);
    // 도로명주소 포함 여부 (도로명주소의 일부 일치)
    List<Naver> findByroadAddressInclude(String roadNameAddress);
    // 업종별 분류
    List<Naver> findByBusiness(String typeOfBusiness);
    // 평점 오름차순
    List<Naver> OrderByStarScore(String starScore);
    // 평점 내림차순
    List<Naver> OrderByStarScoreDESC(String starScore);
    // 리뷰 갯수 오름차순
    List<Naver> OrderByReview(String reviewCnt);
    // 리뷰 갯수 내림차순
    List<Naver> OrderByReviewDESC(String reviewCnt);
    // 평점 4점 이상
    List<Naver> BetweenStarScoreHigh(String starScore);
    // 평점 1점 이하
    List<Naver> BetweenStarScoreLow(String starScore);
    // 리뷰 1000개 이상
    List<Naver> BetweenReviewHigh(String reviewCnt);
    // 리뷰 10개 이하
    List<Naver> BetweenReviewLow(String reviewCnt);
}
