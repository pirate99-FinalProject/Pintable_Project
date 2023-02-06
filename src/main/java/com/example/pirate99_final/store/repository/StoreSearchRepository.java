package com.example.pirate99_final.store.repository;

import com.example.pirate99_final.store.entity.StoreDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface StoreSearchRepository extends ElasticsearchRepository<StoreDocument,Long> {

    // 검색어
    List<StoreDocument> findTop10ByStoreName(String storeName);
    List<StoreDocument> findTop10ByRoadNameAddress(String roadNameAddress);
    List<StoreDocument> findTop10ByTypeOfBusiness(String typeOfBusiness);

    // 평점 4점 이상
    List<StoreDocument> findTop10ByStoreNameAndStarScoreBetween(String storeName, double min, double max);
    List<StoreDocument> findTop10ByRoadNameAddressAndStarScoreBetween(String roadNameAddress, double min, double max);
    List<StoreDocument> findTop10ByTypeOfBusinessAndStarScoreBetween(String typeOfBusiness, double min, double max);

    // 평점 높은순
    List<StoreDocument> findTop10ByStoreNameOrderByStarScoreDesc(String storeName);
    List<StoreDocument> findTop10ByRoadNameAddressOrderByStarScoreDesc(String roadNameAddress);
    List<StoreDocument> findTop10ByTypeOfBusinessOrderByStarScoreDesc(String typeOfBusiness);

    // 리뷰 1000개 이상
    List<StoreDocument> findTop10ByStoreNameAndReviewCntBetween(String storeName, int min, int max);
    List<StoreDocument> findTop10ByRoadNameAddressAndReviewCntBetween(String roadNameAddress, int min, int max);
    List<StoreDocument> findTop10ByTypeOfBusinessAndReviewCntBetween(String typeOfBusiness, int min, int max);

    // 리뷰 많은순
    List<StoreDocument> findTop10ByStoreNameOrderByReviewCntDesc(String storeName);
    List<StoreDocument> findTop10ByRoadNameAddressOrderByReviewCntDesc(String roadNameAddress);
    List<StoreDocument> findTop10ByTypeOfBusinessOrderByReviewCntDesc(String typeOfBusiness);
}
