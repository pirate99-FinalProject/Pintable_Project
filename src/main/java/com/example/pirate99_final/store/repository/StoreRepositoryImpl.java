package com.example.pirate99_final.store.repository;

import com.example.pirate99_final.store.config.SearchCondition;
import com.example.pirate99_final.store.dto.QuerydslDto;
import com.example.pirate99_final.store.entity.QStore;
import com.example.pirate99_final.store.entity.QStoreStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
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

    QStoreStatus storeStatus = QStoreStatus.storeStatus;

    public List<QuerydslDto> DynamicSQL(SearchCondition condition, String select) {
        List<QuerydslDto> DynamicSQL = queryFactory
                .select(Projections.constructor(QuerydslDto.class, store.storeId,store.address, store.roadNameAddress, store.postNumber, store.storeName, store.typeOfBusiness, store.xCoordinate, store.yCoordinate, storeStatus.waitingCnt, storeStatus.limitWaitingCnt, store.starScore, store.reviewCnt))
                .from(store)
                .join(storeStatus).on(store.storeId.eq(storeStatus.store.storeId))
                .limit(10)
                .where(
                        searchCondition(condition.getAddress(), condition.getRoadNameAddress(), condition.getStoreName(), condition.getTypeOfBusiness(), condition.getStarScore(), condition.getReviewCnt(), select)
//                        eqstoreName(condition.getStoreName()),
//                        eqstoreNameInclude(condition.getStoreName()),
//                        eqroadNameAddress(condition.getRoadNameAddress()),
//                        eqtypeOfBusiness(condition.getTypeOfBusiness()),
//                        eqbetweenStarScore(condition.getStarScore(), select),
//                        eqbetweenReview(condition.getReviewCnt(), select)
                )
                .orderBy(eqSort(select))
                .fetch();
        return DynamicSQL;
    }

    private BooleanBuilder searchCondition(String address, String roadNameAddress,  String storeName, String typeofBusiness, double starScore, int reviewCnt, String select){

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(org.springframework.util.StringUtils.hasText(address)){
            booleanBuilder.or(store.storeName.eq(storeName));
        }

        if(org.springframework.util.StringUtils.hasText(storeName)){
            booleanBuilder.or(store.storeName.contains(storeName));
        }

        if(org.springframework.util.StringUtils.hasText(roadNameAddress)){
            booleanBuilder.or(store.roadNameAddress.contains(roadNameAddress));
        }

        if(org.springframework.util.StringUtils.hasText(typeofBusiness)){
            booleanBuilder.or(store.typeOfBusiness.eq(typeofBusiness));
        }

        if(select.equals("StarScoreLow")){
            booleanBuilder.or(store.starScore.between(0, starScore));
        }

        if(select.equals("ReviewLow")){
            booleanBuilder.or(store.reviewCnt.between(0, reviewCnt));
        }

        return booleanBuilder;
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