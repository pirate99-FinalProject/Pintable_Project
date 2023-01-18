package com.example.pirate99_final.store.repository;

import com.example.pirate99_final.store.config.SearchCondition;
import com.example.pirate99_final.store.entity.QStore;
import com.example.pirate99_final.store.entity.Store;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QStore store = QStore.store;                                                            // query dsl의 QEntity 선언

    public List<Store> DynamicSQL(SearchCondition condition, String select) {
        return queryFactory
                .selectFrom(store)
                .limit(10)
                .where( eqstoreName(condition.getStoreName(), select),
                        eqstoreNameInclude(condition.getStoreName(), select),
                        eqroadNameAddress(condition.getRoadNameAddress()),
                        eqtypeOfBusiness(condition.getTypeOfBusiness()),
                        eqbetweenStarScore(condition.getStarScore(), select),
                        eqbetweenReview(condition.getReviewCnt(), select),
                        eqnotNull(select)
                )
                .orderBy(eqSort(select))
                .fetch();
    }

    private BooleanExpression eqstoreName(String storeName, String select) {                 // 가게이름일치 관련 동적쿼리
        if (select.equals("storeName")) {
            return store.storeName.eq(storeName);
        }
        return null;
    }
    private BooleanExpression eqstoreNameInclude(String storeName, String select) {          // 가게이름포함 관련 동적쿼리
        if (select.equals("storeNameInclude")) {
            return store.storeName.contains(storeName);
        }
        return null;
    }
    private BooleanExpression eqroadNameAddress(String roadNameAddress) {                    // 도로명 주소 관련 동적쿼리
        if (StringUtils.isEmpty(roadNameAddress)) {
            return null;
        }
        return store.roadNameAddress.contains(roadNameAddress);
    }
    private BooleanExpression eqtypeOfBusiness(String typeOfBusiness) {                      // 업종관련 동적쿼리
        if (StringUtils.isEmpty(typeOfBusiness)) {
            return null;
        }
        return store.typeOfBusiness.eq(typeOfBusiness);
    }
    private BooleanExpression eqbetweenStarScore(double starScore, String select) {          // 별점 관련 동적쿼리
        if (starScore == 0) {
            return null;
        } else if (select.equals("StarScoreLow")) {
            return store.starScore.between(0, starScore);
        }
        return store.starScore.between(starScore, 5);
    }
    private BooleanExpression eqbetweenReview(int reviewCnt, String select) {                // 리뷰 관련 동적쿼리
        if (reviewCnt == 0) {
            return null;
        } else if (select.equals("ReviewLow")) {
            return store.reviewCnt.between(0, reviewCnt);
        }
        return store.reviewCnt.between(reviewCnt, 100000);
    }

    private BooleanExpression eqnotNull(String select) {
        if (select.equals("ReviewASC") || select.equals("StarScoreASC")) {
            return store.reviewCnt.isNotNull().and(store.starScore.isNotNull());
        } else {
            return null;
        }
    }
    private OrderSpecifier<?> eqSort(String select) {                                        // 정렬 관련 동적쿼리
        if (select.equals( "StarScoreASC")) {
            return store.starScore.asc();
        } else if (select.equals("ReviewASC")) {
            return store.reviewCnt.asc();
        } else if (select.contains("StarScore")) {
            return store.starScore.desc();
        } else if (select.contains("Review")) {
            return store.reviewCnt.desc();
        } else if (select.contains("Business")) {
            return store.typeOfBusiness.asc();
        } else if (select.equals("roadAddressInclude")) {
            return store.roadNameAddress.asc();
        } else if (select.equals("storeName")) {
            return store.storeName.asc();
        } else if (select.equals("storeNameInclude")) {
            return store.storeName.asc();
        }
        return store.storeId.asc();
    }
}
