//package com.example.pirate99_final.map.repository;
//
//import com.example.pirate99_final.map.entity.Naver;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//public interface NaverRepository extends JpaRepository<Naver, Long> {
////    List<Naver> findByStorenameContaining(String storeName);
////
////    // 1. 현재 위치 반경 20km 이내 검색
////    @Query(
////            value = "SELECT *, (6371*acos(cos(radians(37.4685225))*cos(radians(:latitude))*cos(radians(:longitude) -radians(126.8943311))+sin(radians(37.4685225))*sin(radians(:latitude)))) AS distance From naver where storename LIKE %:storeName% HAVING distance < 50 ORDER BY distance LIMIT 0 , 20"
////            ,nativeQuery = true)
////    List<Naver> searchCurrent(String latitude, String longitude, String storeName);
//}
//
//
