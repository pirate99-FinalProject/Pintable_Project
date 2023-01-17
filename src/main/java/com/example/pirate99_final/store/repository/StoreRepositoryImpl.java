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
        List<Store> DynamicSQL = queryFactory
                .selectFrom(store)
                .limit(10)
                .where(
                        eqstoreName(condition.getStoreName()),
                        eqstoreNameInclude(condition.getStoreName()),
                        eqroadNameAddress(condition.getRoadNameAddress()),
                        eqtypeOfBusiness(condition.getTypeOfBusiness()),
                        eqbetweenStarScore(condition.getStarScore(), select),
                        eqbetweenReview(condition.getReviewCnt(), select)
                )
                .orderBy(eqSort(select))
                .fetch();
        return DynamicSQL;
    }

    private BooleanExpression eqstoreName(String storeName) {                                // 가게이름 관련 동적쿼리
        if (StringUtils.isEmpty(storeName)) {
            return null;
        }
        return store.storeName.eq(storeName);
    }
    private BooleanExpression eqstoreNameInclude(String storeName) {                         // 가게 이름 관련 동적쿼리
        if (StringUtils.isEmpty(storeName)) {
            return null;
        }
        return store.storeName.contains(storeName);
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
        } else if (select.equals("StarScoreASC")) {
            return store.starScore.between(0, starScore);
        }
        return store.starScore.between(starScore, 5);
    }
    private BooleanExpression eqbetweenReview(int reviewCnt, String select) {                // 리뷰 관련 동적쿼리
        if (reviewCnt == 0) {
            return null;
        } else if (select.equals("ReviewASC")) {
            return store.reviewCnt.between(0, reviewCnt);
        }
        return store.reviewCnt.between(reviewCnt, 100000);
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
            return store.typeOfBusiness.desc();
        } else if (select.contains("roadAddress")) {
            return store.roadNameAddress.desc();
        } else if (select.contains("storeName")) {
            return store.storeName.desc();
        }
        return store.storeId.desc();
    }
}
