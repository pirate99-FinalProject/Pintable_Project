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
    QNaver naver = QNaver.naver;

    /*
      기능 : 입력한 storeName과 정확히 일치하는 가게명 한곳을 찾는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public Naver findByStoreName(String storeName) {                        // 가게명 일치 여부
        QNaver naver = QNaver.naver;                                        // query dsl의 QEntity 선언
        Naver findByStoreName = queryFactory
                .selectFrom(naver)
                .where(naver.storename.eq(storeName))                       // DB의 storename과 입력한 파라메터 storeName Equal
                .fetchOne();                                                // 단 건 조회(없을시 null, 2개이상 NonUniqueResultException)
        return findByStoreName;
    }

    /*
	  기능 : 입력한 storeNameInclude를 포함하는 가게명을 찾는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Naver> findByStoreNameInclude(String storeName) {           // 가게명 포함 여부 ( 가게명의 일부 일치 )
        QNaver naver = QNaver.naver;                                        // query dsl의 QEntity 선언
        List<Naver> StoreNameInclude = queryFactory
                .selectFrom(naver)
                .limit(10)                                                  // 출력값 갯수제한 10개
                .where(naver.storename.contains(storeName))                 // 입력한 storeName이 db의 storename에 포함되어 있는지 여부 확인
                .fetch();                                                   // 리스트로 결과 반환
        return StoreNameInclude;
    }

    /*
	  기능 : 입력한 roadNameAddress를 포함하는 가게의 도로명주소를 찾는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Naver> findByroadAddressInclude(String roadNameAddress) {   // 도로명주소 포함 여부 (도로명주소의 일부 일치)
        QNaver naver = QNaver.naver;
        List<Naver> roadAddressInclude = queryFactory
                .selectFrom(naver)
                .limit(10)
                .where(naver.roadnameaddress.contains(roadNameAddress))     // 입력한 roadNameAddress가 DB의 roadNameAddress에 포함되어 있는지 여부 확인
                .fetch();
        return roadAddressInclude;
    }

    /*
	  기능 : 입력한 typeOfBusiness와 관련된 업종을 가진 가게를 찾는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Naver> findByBusiness(String typeOfBusiness) {              // 업종별 분류
        QNaver naver = QNaver.naver;
        List<Naver> businessType = queryFactory
                .selectFrom(naver)
                .limit(10)
                .where(naver.typeofbusiness.eq(typeOfBusiness))             // DB에서 동일한 업종을 가진 가게를 보여줌
                .fetch();
        return businessType;
    }

    /*
	  기능 : 가게를 평점이 낮은순으로 정렬하는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Naver> OrderByStarScore() {                                 // 평점 낮은순 ASC
        QNaver naver = QNaver.naver;
        List<Naver> starScoreASC = queryFactory
                .selectFrom(naver)
                .limit(10)
                .orderBy(naver.starscore.asc())                             // 평점을 낮은순으로 정렬
                .fetch();
        return starScoreASC;
    }

    /*
     기능 : 가게를 평점이 높은순으로 정렬하는 기능
     작성자 : 이상훈
     작성일자 : 22.1.10
     수정일자 : 22.1.11
   */
    public List<Naver> OrderByStarScoreDESC() {                             // 평점 높은순 DESC
        QNaver naver = QNaver.naver;
        List<Naver> starScoreDESC = queryFactory
                .selectFrom(naver)
                .limit(10)
                .orderBy(naver.starscore.desc())                            // 평점을 높은순으로 정렬
                .fetch();
        return starScoreDESC;
    }

    /*
	  기능 : 가게를 리뷰가 낮은순으로 정렬하는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Naver> OrderByReview() {                                    // 리뷰 낮은순 ASC
        QNaver naver = QNaver.naver;
        List<Naver> reviewASC = queryFactory
                .selectFrom(naver)
                .limit(10)
                .orderBy(naver.reviewcnt.asc())
                .fetch();
        return reviewASC;
    }

    /*
      기능 : 가게를 리뷰가 높은순으로 정렬하는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Naver> OrderByReviewDESC() {                                // 리뷰 높은순 DESC
        QNaver naver = QNaver.naver;
        List<Naver> reviewDESC = queryFactory
                .selectFrom(naver)
                .limit(10)
                .orderBy(naver.reviewcnt.desc())
                .fetch();
        return reviewDESC;
    }

    /*
      기능 : 가게 평점이 입력값 이상인 가게만 보여주는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Naver> BetweenStarScoreHigh(double score) {                 // 평점 4점 이상
        QNaver naver = QNaver.naver;
        List<Naver> BetweenStarScoreHigh = queryFactory
                .selectFrom(naver)
                .limit(10)
                .where(naver.starscore.between(score, 5))                   // 최소 4점에서 5점
                .orderBy(naver.starscore.desc())
                .fetch();
        return BetweenStarScoreHigh;
    }

    /*
      기능 : 가게 평점이 입력값 이하인 가게만 보여주는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Naver> BetweenStarScoreLow(double score) {                  // 평점 2점 이하
        QNaver naver = QNaver.naver;
        List<Naver> BetweenStarScoreLow = queryFactory
                .selectFrom(naver)
                .limit(10)
                .where(naver.starscore.between(0, score))                   // 최소 0점에서 1점
                .orderBy(naver.starscore.desc())
                .fetch();
        return BetweenStarScoreLow;
    }

    /*
      기능 : 가게 리뷰가 1000개이상인 가게만 보여주는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Naver> BetweenReviewHigh(int review) {                      // 리뷰 1000개 이상
        QNaver naver = QNaver.naver;
        List<Naver> BetweenReviewHigh = queryFactory
                .selectFrom(naver)
                .limit(10)
                .where(naver.reviewcnt.between(review, 1000000))            // 최소 1000개부터 백만
                .orderBy(naver.reviewcnt.desc())
                .fetch();
        return BetweenReviewHigh;
    }

    /*
      기능 : 가게 리뷰가 10개이하인 가게만 보여주는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public List<Naver> BetweenReviewLow(int review) {                        // 리뷰 10개 이하
        QNaver naver = QNaver.naver;
        List<Naver> BetweenReviewLow = queryFactory
                .selectFrom(naver)
                .limit(10)
                .where(naver.reviewcnt.between(0, review))                   // 최소 0개에서 10개
                .orderBy(naver.reviewcnt.desc())
                .fetch();
        return BetweenReviewLow;
    }
}
