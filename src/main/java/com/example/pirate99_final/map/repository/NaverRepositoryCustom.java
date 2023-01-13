package com.example.pirate99_final.map.repository;

import com.example.pirate99_final.store.entity.Store;

import java.util.List;

public interface NaverRepositoryCustom {
    List<Store> findByStoreName(String storeName);                  // 가게명 일치 여부

    List<Store> findByStoreNameInclude(String storeName);           // 가게명 포함 여부 (가게명의 일부 일치)

    List<Store> findByroadAddressInclude(String roadNameAddress);   // 도로명주소 포함 여부 (도로명주소의 일부 일치)

    List<Store> findByBusiness(String typeOfBusiness);              // 업종별 분류

    List<Store> OrderByStarScore();                                 // 평점 오름차순

    List<Store> OrderByStarScoreDESC();                             // 평점 내림차순

    List<Store> OrderByReview();                                    // 리뷰 갯수 오름차순

    List<Store> OrderByReviewDESC();                                // 리뷰 갯수 내림차순

    List<Store> BetweenStarScoreHigh(double score);                 // 평점 4점 이상

    List<Store> BetweenStarScoreLow(double score);                  // 평점 1점 이하

    List<Store> BetweenReviewHigh(int review);                      // 리뷰 1000개 이상

    List<Store> BetweenReviewLow(int review);                       // 리뷰 10개 이하
}
