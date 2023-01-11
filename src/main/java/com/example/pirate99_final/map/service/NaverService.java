//package com.example.pirate99_final.map.service;
//
//import com.example.pirate99_final.map.entity.Naver;
//import com.example.pirate99_final.map.repository.NaverRepository;
//import com.example.pirate99_final.map.repository.NaverRepositoryImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//import java.util.List;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class NaverService {
//    private final NaverRepository naverRepository;
//    private final NaverRepositoryImpl naverRepositoryImpl;
//
//    // 가게명 일치 여부
//    public void findByOneStoreName(Model model, String storeName) {
//        Naver findByOneStoreName = naverRepositoryImpl.findByStoreName(storeName);
//        model.addAttribute("Lat", findByOneStoreName.getLat());
//        model.addAttribute("Lng", findByOneStoreName.getLng());
//    }
//
//    // 가게명 일부 포함
//    public void findByStoreNameInclude(Model model, String storeNameInclude) {
//        List<Naver> findByStoreNameInclude = naverRepositoryImpl.findByStoreNameInclude(storeNameInclude);
//        model.addAttribute("searchList", findByStoreNameInclude);
//    }
//
//    // 도로명주소 포함 여부 (도로명주소의 일부 일치)
//    public void findByRoadAddressInclude(Model model, String roadNameAddress) {
//        List<Naver> findByRoadAddressInclude = naverRepositoryImpl.findByroadAddressInclude(roadNameAddress);
//        model.addAttribute("searchList", findByRoadAddressInclude);
//    }
//
//    // 업종별 분류
//    public void findByBusiness(Model model, String typeOfBusiness) {
//        List<Naver> findByBusiness = naverRepositoryImpl.findByBusiness(typeOfBusiness);
//        model.addAttribute("searchList", findByBusiness);
//    }
//
//    // 평점 낮은순 ASC
//    public void OrderByStarScoreASC(Model model) {
//        List<Naver> OrderByStarScoreASC = naverRepositoryImpl.OrderByStarScore();
//        model.addAttribute("searchList", OrderByStarScoreASC);
//    }
//
//    // 평점 높은순 DESC
//    public void OrderByStarScoreDESC(Model model) {
//        List<Naver> OrderByStarScoreDESC = naverRepositoryImpl.OrderByStarScoreDESC();
//        model.addAttribute("searchList", OrderByStarScoreDESC);
//    }
//
//    // 리뷰 낮은순 ASC
//    public void OrderByReviewASC(Model model) {
//        List<Naver> OrderByReviewASC = naverRepositoryImpl.OrderByReview();
//        model.addAttribute("searchList", OrderByReviewASC);
//    }
//
//    // 리뷰 높은순 DESC
//    public void OrderByReviewDESC(Model model) {
//        List<Naver> OrderByReviewDESC = naverRepositoryImpl.OrderByReviewDESC();
//        model.addAttribute("searchList", OrderByReviewDESC);
//    }
//
//    // 평점 4점 이상
//    public void BetweenStarScoreHigh(Model model) {
//        List<Naver> BetweenStarScoreHigh = naverRepositoryImpl.BetweenStarScoreHigh();
//        model.addAttribute("searchList", BetweenStarScoreHigh);
//    }
//
//    // 평점 2점 이하
//    public void BetweenStarScoreLow(Model model) {
//        List<Naver> BetweenStarScoreLow = naverRepositoryImpl.BetweenStarScoreLow();
//        model.addAttribute("searchList", BetweenStarScoreLow);
//    }
//
//    // 리뷰 1000개 이상
//    public void BetweenReviewHigh(Model model) {
//        List<Naver> BetweenReviewHigh = naverRepositoryImpl.BetweenReviewHigh();
//        model.addAttribute("searchList", BetweenReviewHigh);
//    }
//
//    // 리뷰 10개 이하
//    public void BetweenReviewLow(Model model) {
//        List<Naver> BetweenReviewLow = naverRepositoryImpl.BetweenReviewLow();
//        model.addAttribute("searchList", BetweenReviewLow);
//    }
//
//}
//    /*
//	  기능 : 현재 위치에서 검색 기능
//      작성자 : 김규리
//      작성일자 : 22.1.10
//    */
////    public void searchCurrentMap(Model model, String latitude, String longitude, String storeName) {
////
////        List<Naver> naverList = naverRepository.searchCurrent(latitude, longitude, storeName);                           // 1. 입력받은 위도, 경도, 가게 이름으로 DB에 검사한다.
////        model.addAttribute("searchList", naverList);                                                         // 2. index.html에 검색한 결과 전달
////    }
////
////    /*
////	  기능 : 지도 검색 기능
////      작성자 : 김규리
////      작성일자 : 22.1.10
////    */
////    public void searchMap(Model model, String storeName) {
////        String storeNameTrim = storeName.replaceAll(" ", "");                                            // 1. 검색 시 키워드 검색을 위한 문자 치환(" ", "")
////        List<Naver> naverList = naverRepository.findByStorenameContaining(storeNameTrim);                                // 2. %Like% 로 장소 검색
////        model.addAttribute("searchList", naverList);                                                         // 3. index.html에 검색한 결과 전달
////
////    }
////}
////
