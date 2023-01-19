package com.example.pirate99_final.store.repository;

import com.example.pirate99_final.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAllBy();
    List<Store> findByStoreNameContaining(String storeName);

    // 1. 현재 위치 반경 20km 이내 검색
    @Query(
            value = "SELECT *, (6371*acos(cos(radians(37.4685225))*cos(radians(:latitude))*cos(radians(:longitude) -radians(126.8943311))+sin(radians(37.4685225))*sin(radians(:latitude)))) AS distance From store where store_name LIKE %:storeName% HAVING distance < 50 ORDER BY distance LIMIT 0 , 20"
            ,nativeQuery = true)
    List<Store> searchCurrent(String latitude, String longitude, String storeName);

//    Store findByStoreName(String storeName);

    List<Store> findByRoadNameAddress(String roadNameAddress);

    List<Store> findByPostNumber(int postNumber);

    List<Store> findByTypeOfBusiness(String typeOfBusiness);

    List<Store> findByStarScore(double starScore);

    List<Store> findByReviewCnt(int reviewCnt);

    List<Store> findByStoreName(String storeName);
}
