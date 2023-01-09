package com.example.pirate99_final.map.repository;

import com.example.pirate99_final.map.entity.Naver;
import com.example.pirate99_final.map.entity.QNaver;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NaverRepositoryImpl implements NaverRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    // 가게명 일치 여부
    public Naver findByStoreName(String storeName) {
        QNaver naver = QNaver.naver;
        Naver findByStoreName = queryFactory
                .selectFrom(naver)
                .where(naver.storeName.eq(storeName))
                .fetchOne();
        return findByStoreName;
    }
    // 가게명 포함 여부 ( 가게명의 일부 일치 )
    public List<Naver> findByStoreNameInclude(String storeName) {
        QNaver naver = QNaver.naver;
        List<Naver> StoreNameInclude = queryFactory
                .selectFrom(naver)
                .where(naver.storeName.contains(storeName))
                .fetch();
        return StoreNameInclude;
    }
    // 도로명주소 포함 여부 (도로명주소의 일부 일치)
    public List<Naver> findByroadAddressInclude(String roadNameAddress) {
        QNaver naver = QNaver.naver;
        List<Naver> roadAddressInclude = queryFactory
                .selectFrom(naver)
                .where(naver.roadNameAddress.contains(roadNameAddress))
                .fetch();
        return roadAddressInclude;
    }
    // 업종별 분류
    public List<Naver> findByBusiness(String typeOfBusiness) {
        QNaver naver = QNaver.naver;
        List<Naver> businessType = queryFactory
                .selectFrom(naver)
                .where(naver.typeOfBusiness.eq(typeOfBusiness))
                .fetch();
        return businessType;
    }
    // 평점 낮은순 ASC
    public List<Naver> OrderByStarScore(String starScore) {
        QNaver naver = QNaver.naver;
        List<Naver> starScoreASC = queryFactory
                .selectFrom(naver)
                .orderBy(naver.starScore.asc())
                .fetch();
        return starScoreASC;
    }
    // 평점 높은순 DESC
    public List<Naver> OrderByStarScoreDESC(String starScore) {
        QNaver naver = QNaver.naver;
        List<Naver> starScoreDESC = queryFactory
                .selectFrom(naver)
                .orderBy(naver.starScore.desc())
                .fetch();
        return starScoreDESC;
    }
    // 리뷰 낮은순 ASC
    public List<Naver> OrderByReview(String reviewCnt) {
        QNaver naver = QNaver.naver;
        List<Naver> reviewASC = queryFactory
                .selectFrom(naver)
                .orderBy(naver.reviewCnt.asc())
                .fetch();
        return reviewASC;
    }
    // 리뷰 높은순 DESC
    public List<Naver> OrderByReviewDESC(String reviewCnt) {
        QNaver naver = QNaver.naver;
        List<Naver> reviewDESC = queryFactory
                .selectFrom(naver)
                .orderBy(naver.reviewCnt.desc())
                .fetch();
        return reviewDESC;
    }
    // 평점 4점 이상
    public List<Naver> BetweenStarScoreHigh(String starScore) {
        QNaver naver = QNaver.naver;
        List<Naver> BetweenStarScoreHigh = queryFactory
                .selectFrom(naver)
                .where(naver.starScore.between(4, 5))           // 최소 4점에서 5점
                .orderBy(naver.starScore.desc())
                .fetch();
        return BetweenStarScoreHigh;
    }
    // 평점 1점 이하
    public List<Naver> BetweenStarScoreLow(String starScore) {
        QNaver naver = QNaver.naver;
        List<Naver> BetweenStarScoreLow = queryFactory
                .selectFrom(naver)
                .where(naver.starScore.between(0, 1))          // 최소 0점에서 1점
                .orderBy(naver.starScore.desc())
                .fetch();
        return BetweenStarScoreLow;
    }
    // 리뷰 1000개 이상
    public List<Naver> BetweenReviewHigh(String reviewCnt) {
        QNaver naver = QNaver.naver;
        List<Naver> BetweenReviewHigh = queryFactory
                .selectFrom(naver)
                .where(naver.reviewCnt.between(1000, 1000000)) // 최소 1000개부터 백만
                .orderBy(naver.reviewCnt.desc())
                .fetch();
        return BetweenReviewHigh;
    }
    // 리뷰 10개 이하
    public List<Naver> BetweenReviewLow(String reviewCnt) {
        QNaver naver = QNaver.naver;
        List<Naver> BetweenReviewLow = queryFactory
                .selectFrom(naver)
                .where(naver.reviewCnt.between(0, 10))          // 최소 0개에서 10개
                .orderBy(naver.reviewCnt.desc())
                .fetch();
        return BetweenReviewLow;
    }
}
