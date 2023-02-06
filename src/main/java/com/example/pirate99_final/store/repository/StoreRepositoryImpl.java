package com.example.pirate99_final.store.repository;

import com.example.pirate99_final.store.config.SearchCondition;
import com.example.pirate99_final.store.dto.QuerydslDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import static com.example.pirate99_final.store.entity.QStore.*;                                          // Qentity 선언
import static com.example.pirate99_final.store.entity.QStoreStatus.*;                                    // Qentity 선언

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<QuerydslDto> DynamicSQL(SearchCondition condition, String select) {
        return queryFactory
                .selectDistinct(Projections.constructor(                                                // select 조건 중에서 중복 발생시 제거
                        QuerydslDto.class,                                                              // dto방식으로 반환 받도록 수정
                        store.storeId,
                        store.address,
                        store.roadNameAddress,
                        store.postNumber,
                        store.storeName,
                        store.typeOfBusiness,
                        store.xCoordinate,
                        store.yCoordinate,
                        storeStatus.waitingCnt,                                                         // 예약 시스템 로직상 추가
                        storeStatus.limitWaitingCnt,                                                    // 예약 시스템 로직상 추가
                        store.starScore,
                        store.reviewCnt))
                .from(store)
                .join(storeStatus).on(store.storeId.eq(storeStatus.store.storeId))                      // 예약 시스템 로직상 추가
                .limit(limitCnt(condition))
                .where(
                        searchCondition(
                                condition,                                                              // 가게이름, 도로명주소, 업종, 별점, 리뷰
                                select),                                                                // url
                        eqnotNull(select)                                                               // notNull 처리
                )
                .orderBy(
                        OrderSearchCondition(                                                           // 정렬처리
                                condition,                                                              // 가게이름, 도로명주소, 업종
                                select))                                                                // 별점, 리뷰
                .fetch();
    }

    private BooleanBuilder searchCondition(SearchCondition condition, String select) {                  // where절 booleanbuilder를 통한 동적쿼리 처리

        BooleanBuilder booleanBuilder = new BooleanBuilder();                                           // booleanbuilder 선언

        if (org.springframework.util.StringUtils.hasText(condition.getStoreName())) {                   // StringUtils 라이브러리를 이용해 매개변수가 가게이름을 가졌을때 if문에서 잡아줌
            NumberTemplate booleanTemplate = Expressions.numberTemplate(Double.class,                   // full-text index 적용
                    "function('fullTextSearch',{0},{1})", store.storeName, "+" + condition.getStoreName() + "*"); // config의 Dialect를 활용해 Full-text index 쿼리문 동작, in boolean mode
            if (select.equals("StarScore")) {
                booleanBuilder.or(booleanTemplate.gt(0)).and(store.starScore.between(4, 5));
            }else if (select.equals("Review")) {
                booleanBuilder.or(booleanTemplate.gt(0)).and(store.reviewCnt.between(1000, 100000));
            }else {
                booleanBuilder.or(booleanTemplate.gt(0));
            }
        }

        if (org.springframework.util.StringUtils.hasText(condition.getRoadNameAddress())) {             // StringUtils 라이브러리를 이용해 매개변수가 도로명 주소를 가졌을때 if문에서 잡아줌
            NumberTemplate booleanTemplate = Expressions.numberTemplate(Double.class,                   // full-text index 적용
                    "function('fullTextSearch',{0},{1})", store.roadNameAddress, "+" + condition.getRoadNameAddress() + "*"); // config의 Dialect를 활용해 Full-text index 쿼리문 동작, in boolean mode
            if (select.equals("StarScore")) {
                booleanBuilder.or(booleanTemplate.gt(0)).and(store.starScore.between(4, 5));
            }else if (select.equals("Review")) {
                booleanBuilder.or(booleanTemplate.gt(0)).and(store.reviewCnt.between(1000, 100000));
            }else {
                booleanBuilder.or(booleanTemplate.gt(0));
            }
        }

        if (org.springframework.util.StringUtils.hasText(condition.getTypeOfBusiness())) {              // 업종관련 검색 조건
            NumberTemplate booleanTemplate = Expressions.numberTemplate(Double.class,                   // full-text index 적용
                    "function('fullTextSearch',{0},{1})", store.typeOfBusiness, "+" + condition.getTypeOfBusiness() + "*"); // config의 Dialect를 활용해 Full-text index 쿼리문 동작, in boolean mode
            if (select.equals("StarScore")) {
                booleanBuilder.or(booleanTemplate.gt(0)).and(store.starScore.between(4, 5));
            }else if (select.equals("Review")) {
                booleanBuilder.or(booleanTemplate.gt(0)).and(store.reviewCnt.between(1000, 100000));
            }else {
                booleanBuilder.or(booleanTemplate.gt(0));
            }

        }
        return booleanBuilder;
    }

    private int limitCnt(SearchCondition condition) {
        if (!(org.apache.commons.lang3.StringUtils.isEmpty(condition.getStoreName()))) {
            return 10;
        }
        if (!(org.apache.commons.lang3.StringUtils.isEmpty(condition.getRoadNameAddress()))) {
            return 10;
        }
        if (!(org.apache.commons.lang3.StringUtils.isEmpty(condition.getTypeOfBusiness()))) {
            return 10;
        }
        return 1000;
    }

    private BooleanExpression eqnotNull(String select) {                                                // not null처리
        if (select.equals("ReviewASC") || select.equals("StarScoreASC")) {                              // 평점 혹은 리뷰 낮은순으로 했을시 null값이 가장 먼저 나오기 때문에
            return store.reviewCnt.isNotNull().or(store.starScore.isNotNull());                         // null값에 대한 예외처리
        } else {
            return null;
        }
    }

    private OrderSpecifier<?> OrderSearchCondition(SearchCondition condition, String select) {          // 정렬 관련 동적쿼리

        if (org.springframework.util.StringUtils.hasText(condition.getStoreName())) {                   // 가게이름 관련 정렬
            if (select.contains("StarScore") || select.contains("Review")) {
                return OrderScoreAndCnt(condition,select);
            }
            return store.storeName.asc();
        }

        if (org.springframework.util.StringUtils.hasText(condition.getRoadNameAddress())) {             // 도로명주소 관련 정렬
            if (select.contains("StarScore") || select.contains("Review")) {
                return OrderScoreAndCnt(condition,select);
            }
            return store.roadNameAddress.asc();
        }

        if (org.springframework.util.StringUtils.hasText(condition.getTypeOfBusiness())) {              // 업종 관련 정렬
            if (select.contains("StarScore") || select.contains("Review")) {
                return OrderScoreAndCnt(condition,select);
            }
            return store.typeOfBusiness.asc();
        }
        return null;
    }

    private OrderSpecifier<?> OrderScoreAndCnt(SearchCondition condition, String select) {
        if (org.springframework.util.StringUtils.hasText(condition.getStoreName())) {                   // 가게이름 관련 정렬
            return select.equals("StarScoreDESC") ? store.starScore.desc() : store.reviewCnt.desc();
        }
        if (org.springframework.util.StringUtils.hasText(condition.getRoadNameAddress())) {             // 도로명주소 관련 정렬
            return select.equals("StarScoreDESC") ? store.starScore.desc() : store.reviewCnt.desc();
        }
        if (org.springframework.util.StringUtils.hasText(condition.getTypeOfBusiness())) {              // 업종 관련 정렬
            return select.equals("StarScoreDESC") ? store.starScore.desc() : store.reviewCnt.desc();
        }
        return null;
    }
}
