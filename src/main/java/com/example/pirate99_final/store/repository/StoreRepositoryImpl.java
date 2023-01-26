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
import org.thymeleaf.util.StringUtils;

import java.util.List;

import static com.example.pirate99_final.store.entity.QStore.*;         // Qentity 선언
import static com.example.pirate99_final.store.entity.QStoreStatus.*;   // Qentity 선언

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<QuerydslDto> DynamicSQL(SearchCondition condition, String select) {
        return queryFactory
                .select(Projections.constructor(QuerydslDto.class, store.storeId,store.address, store.roadNameAddress, store.postNumber, store.storeName, store.typeOfBusiness, store.xCoordinate, store.yCoordinate, storeStatus.waitingCnt, storeStatus.limitWaitingCnt, store.starScore, store.reviewCnt))
                .from(store)
                .join(storeStatus).on(store.storeId.eq(storeStatus.store.storeId))
                .limit(10)
                .where(
                        searchCondition(condition.getRoadNameAddress(), condition.getStoreName(), condition.getTypeOfBusiness(), condition.getStarScore(), condition.getReviewCnt(), select),
                        eqnotNull(select)
                )
//                .where(searchCondition(condition.getStoreName()))
                .orderBy(eqSort(select))
                .fetch();
    }

//    private BooleanExpression searchCondition(String storeName) {
//        if (StringUtils.isEmpty(storeName)) {
//            return null;
//        }
//        NumberTemplate booleanTemplate = Expressions.numberTemplate(Double.class,
//                "function('match',{0},{1})", store.storeName, "+" + storeName + "*");
//        return booleanTemplate.gt(0);
//    }

    private BooleanBuilder searchCondition(String roadNameAddress,  String storeName, String typeofBusiness, double starScore, int reviewCnt, String select){

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(org.springframework.util.StringUtils.hasText(storeName)){
            NumberTemplate booleanTemplate = Expressions.numberTemplate(Double.class,
                    "function('fullTextSearch',{0},{1})", store.storeName, "+" + storeName + "*");
            booleanBuilder.or(booleanTemplate.gt(0));
        }

        if(org.springframework.util.StringUtils.hasText(roadNameAddress)){
            NumberTemplate booleanTemplate = Expressions.numberTemplate(Double.class,
                    "function('fullTextSearch',{0},{1})", store.roadNameAddress, "+" + roadNameAddress + "*");
            booleanBuilder.or(booleanTemplate.gt(0));
        }

        if(org.springframework.util.StringUtils.hasText(typeofBusiness)){
            booleanBuilder.or(store.typeOfBusiness.eq(typeofBusiness));
        }

        if(select.equals("StarScoreLow")){
            booleanBuilder.or(store.starScore.between(0, starScore));
        } else if (select.equals("StarScoreHigh")) {
            booleanBuilder.or(store.starScore.between(starScore, 5));
        }

        if(select.equals("ReviewLow")){
            booleanBuilder.or(store.reviewCnt.between(0, reviewCnt));
        } else if (select.equals("ReviewHigh")) {
            booleanBuilder.or(store.reviewCnt.between(reviewCnt, 100000));
        }

        return booleanBuilder;
    }

    private BooleanExpression eqnotNull(String select) {
        if (select.equals("ReviewASC") || select.equals("StarScoreASC")) {
            return store.reviewCnt.isNotNull().or(store.starScore.isNotNull());
        } else {
            return null;
        }
    }
    private OrderSpecifier<?> eqSort(String select) {
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
        } else if (select.equals("storeNameInclude")) {
            return store.storeName.asc();
        }
        return store.storeId.asc();
    }
}