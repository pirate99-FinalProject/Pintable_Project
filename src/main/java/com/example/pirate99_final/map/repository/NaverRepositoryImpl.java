package com.example.pirate99_final.map.repository;

import com.example.pirate99_final.store.entity.QStore;
import com.example.pirate99_final.store.entity.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NaverRepositoryImpl implements NaverRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QStore store = QStore.store;                                            // query dsl의 QEntity 선언

    /*
      기능 : 입력한 storeName과 정확히 일치하는 가게명 한곳을 찾는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Store> findByStoreName(String storeName) {                  // 가게명 일치 여부
        List<Store> findByStoreName = queryFactory
                .selectFrom(store)
                .where(store.storeName.eq(storeName))                       // DB의 storename과 입력한 파라메터 storeName Equal
                .fetch();                                                   // 단 건 조회(없을시 null, 2개이상 NonUniqueResultException)
        return findByStoreName;
    }

    /*
	  기능 : 입력한 storeNameInclude를 포함하는 가게명을 찾는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Store> findByStoreNameInclude(String storeName) {           // 가게명 포함 여부 ( 가게명의 일부 일치 )
        List<Store> StoreNameInclude = queryFactory
                .selectFrom(store)
                .limit(10)                                                  // 출력값 갯수제한 10개
                .where(store.storeName.contains(storeName))                 // 입력한 storeName이 db의 storename에 포함되어 있는지 여부 확인
                .fetch();                                                   // 리스트로 결과 반환
        return StoreNameInclude;
    }

    /*
	  기능 : 입력한 roadNameAddress를 포함하는 가게의 도로명주소를 찾는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Store> findByroadAddressInclude(String roadNameAddress) {   // 도로명주소 포함 여부 (도로명주소의 일부 일치)
        List<Store> roadAddressInclude = queryFactory
                .selectFrom(store)
                .limit(10)
                .where(store.roadNameAddress.contains(roadNameAddress))     // 입력한 roadNameAddress가 DB의 roadNameAddress에 포함되어 있는지 여부 확인
                .fetch();
        return roadAddressInclude;
    }

    /*
	  기능 : 입력한 typeOfBusiness와 관련된 업종을 가진 가게를 찾는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Store> findByBusiness(String typeOfBusiness) {              // 업종별 분류
        List<Store> businessType = queryFactory
                .selectFrom(store)
                .limit(10)
                .where(store.typeOfBusiness.eq(typeOfBusiness))             // DB에서 동일한 업종을 가진 가게를 보여줌
                .fetch();
        return businessType;
    }

    /*
	  기능 : 가게를 평점이 낮은순으로 정렬하는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Store> OrderByStarScore() {                                 // 평점 낮은순 ASC
        List<Store> starScoreASC = queryFactory
                .selectFrom(store)
                .limit(10)
                .orderBy(store.starScore.asc())                             // 평점을 낮은순으로 정렬
                .fetch();
        return starScoreASC;
    }

    /*
     기능 : 가게를 평점이 높은순으로 정렬하는 기능
     작성자 : 이상훈
     작성일자 : 22.1.10
     수정일자 : 22.1.11
   */
    public List<Store> OrderByStarScoreDESC() {                             // 평점 높은순 DESC
        List<Store> starScoreDESC = queryFactory
                .selectFrom(store)
                .limit(10)
                .orderBy(store.starScore.desc())                            // 평점을 높은순으로 정렬
                .fetch();
        return starScoreDESC;
    }

    /*
	  기능 : 가게를 리뷰가 낮은순으로 정렬하는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Store> OrderByReview() {                                    // 리뷰 낮은순 ASC
        List<Store> reviewASC = queryFactory
                .selectFrom(store)
                .limit(10)
                .orderBy(store.reviewCnt.asc())
                .fetch();
        return reviewASC;
    }

    /*
      기능 : 가게를 리뷰가 높은순으로 정렬하는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Store> OrderByReviewDESC() {                                // 리뷰 높은순 DESC
        List<Store> reviewDESC = queryFactory
                .selectFrom(store)
                .limit(10)
                .orderBy(store.reviewCnt.desc())
                .fetch();
        return reviewDESC;
    }

    /*
      기능 : 가게 평점이 입력값 이상인 가게만 보여주는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Store> BetweenStarScoreHigh(double score) {                 // 평점 4점 이상
        List<Store> BetweenStarScoreHigh = queryFactory
                .selectFrom(store)
                .limit(10)
                .where(store.starScore.between(score, 5))                   // 최소 4점에서 5점
                .orderBy(store.starScore.desc())
                .fetch();
        return BetweenStarScoreHigh;
    }

    /*
      기능 : 가게 평점이 입력값 이하인 가게만 보여주는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Store> BetweenStarScoreLow(double score) {                  // 평점 2점 이하
        List<Store> BetweenStarScoreLow = queryFactory
                .selectFrom(store)
                .limit(10)
                .where(store.starScore.between(0, score))                   // 최소 0점에서 1점
                .orderBy(store.starScore.desc())
                .fetch();
        return BetweenStarScoreLow;
    }

    /*
      기능 : 가게 리뷰가 1000개이상인 가게만 보여주는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Store> BetweenReviewHigh(int review) {                      // 리뷰 1000개 이상
        List<Store> BetweenReviewHigh = queryFactory
                .selectFrom(store)
                .limit(10)
                .where(store.reviewCnt.between(review, 1000000))            // 최소 1000개부터 백만
                .orderBy(store.reviewCnt.desc())
                .fetch();
        return BetweenReviewHigh;
    }

    /*
      기능 : 가게 리뷰가 10개이하인 가게만 보여주는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Store> BetweenReviewLow(int review) {                        // 리뷰 10개 이하
        List<Store> BetweenReviewLow = queryFactory
                .selectFrom(store)
                .limit(10)
                .where(store.reviewCnt.between(0, review))                   // 최소 0개에서 10개
                .orderBy(store.reviewCnt.desc())
                .fetch();
        return BetweenReviewLow;
    }
}
