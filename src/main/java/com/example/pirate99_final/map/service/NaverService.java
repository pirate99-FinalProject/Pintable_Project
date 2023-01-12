package com.example.pirate99_final.map.service;

import com.example.pirate99_final.map.entity.Naver;
import com.example.pirate99_final.map.repository.NaverRepository;
import com.example.pirate99_final.map.repository.NaverRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NaverService {
    private final NaverRepository naverRepository;                                                                      // naverRepository 의존성 주입
    private final NaverRepositoryImpl naverRepositoryImpl;                                                              // query Dsl Repository 의존성 주입

    /*
      기능 : 현재 위치에서 검색 기능
      작성자 : 김규리
      작성일자 : 22.1.10
    */
    public void searchCurrentMap(Model model, String latitude, String longitude, String storeName) {

        List<Naver> naverList = naverRepository.searchCurrent(latitude, longitude, storeName);                           // 1. 입력받은 위도, 경도, 가게 이름으로 DB에 검사한다.
        model.addAttribute("searchList", naverList);                                                         // 2. index.html에 검색한 결과 전달
    }

    /*
	  기능 : 지도 검색 기능
      작성자 : 김규리
      작성일자 : 22.1.10
    */
    public void searchMap(Model model, String storeName) {
        String storeNameTrim = storeName.replaceAll(" ", "");                                            // 1. 검색 시 키워드 검색을 위한 문자 치환(" ", "")
        List<Naver> naverList = naverRepository.findByStorenameContaining(storeNameTrim);                                // 2. %Like% 로 장소 검색
        model.addAttribute("searchList", naverList);                                                         // 3. index.html에 검색한 결과 전달
    }

    /*
	  기능 : 입력한 storeName과 정확히 일치하는 가게명 한곳을 찾는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public void findByOneStoreName(Model model, String storeName) {
        Naver findByOneStoreName = naverRepositoryImpl.findByStoreName(storeName);                                      // 1. naverRepositoryImpl 에서 구현된 메소드로 가게명 찾음
        model.addAttribute("Lat", findByOneStoreName.getXcoordinate());                                      // 2. index로 경도 위도값 보내줌
        model.addAttribute("Lng", findByOneStoreName.getYcoordinate());
    }

    /*
	  기능 : 입력한 storeNameInclude를 포함하는 가게명을 찾는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public void findByStoreNameInclude(Model model, String storeNameInclude) {                                          // 1. naverRepositoryImpl 구현
        List<Naver> findByStoreNameInclude = naverRepositoryImpl.findByStoreNameInclude(storeNameInclude);
        model.addAttribute("searchList", findByStoreNameInclude);
    }

    /*
	  기능 : 입력한 roadNameAddress를 포함하는 가게의 도로명주소를 찾는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public void findByRoadAddressInclude(Model model, String roadNameAddress) {
        List<Naver> findByRoadAddressInclude = naverRepositoryImpl.findByroadAddressInclude(roadNameAddress);           // 1. naverRepositoryImpl 구현
        model.addAttribute("searchList", findByRoadAddressInclude);
    }

    /*
	  기능 : 입력한 typeOfBusiness와 관련된 업종을 가진 가게를 찾는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public void findByBusiness(Model model, String typeOfBusiness) {
        List<Naver> findByBusiness = naverRepositoryImpl.findByBusiness(typeOfBusiness);                                // 1. naverRepositoryImpl 구현
        model.addAttribute("searchList", findByBusiness);
    }

    /*
	  기능 : 가게를 평점이 낮은순으로 정렬하는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public void OrderByStarScoreASC(Model model) {
        List<Naver> OrderByStarScoreASC = naverRepositoryImpl.OrderByStarScore();                                       // 1. naverRepositoryImpl 구현
        model.addAttribute("searchList", OrderByStarScoreASC);
    }

     /*
	  기능 : 가게를 평점이 높은순으로 정렬하는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public void OrderByStarScoreDESC(Model model) {
        List<Naver> OrderByStarScoreDESC = naverRepositoryImpl.OrderByStarScoreDESC();                                  // 1. naverRepositoryImpl 구현
        model.addAttribute("searchList", OrderByStarScoreDESC);
    }

    /*
	  기능 : 가게를 리뷰가 낮은순으로 정렬하는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public void OrderByReviewASC(Model model) {
        List<Naver> OrderByReviewASC = naverRepositoryImpl.OrderByReview();                                             // 1. naverRepositoryImpl 구현
        model.addAttribute("searchList", OrderByReviewASC);
    }

    /*
      기능 : 가게를 리뷰가 높은순으로 정렬하는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public void OrderByReviewDESC(Model model) {
        List<Naver> OrderByReviewDESC = naverRepositoryImpl.OrderByReviewDESC();                                        // 1. naverRepositoryImpl 구현
        model.addAttribute("searchList", OrderByReviewDESC);
    }

    /*
      기능 : 가게 평점이 n점 이상인 가게만 보여주는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public void BetweenStarScoreHigh(Model model, double score) {
        List<Naver> BetweenStarScoreHigh = naverRepositoryImpl.BetweenStarScoreHigh(score);                                  // 1. naverRepositoryImpl 구현
        model.addAttribute("searchList", BetweenStarScoreHigh);
    }

    /*
      기능 : 가게 평점이 n점 이하인 가게만 보여주는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public void BetweenStarScoreLow(Model model, double score) {
        List<Naver> BetweenStarScoreLow = naverRepositoryImpl.BetweenStarScoreLow(score);                                    // 1. naverRepositoryImpl 구현
        model.addAttribute("searchList", BetweenStarScoreLow);
    }

    /*
      기능 : 가게 리뷰가 n개이상인 가게만 보여주는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public void BetweenReviewHigh(Model model, int review) {
        List<Naver> BetweenReviewHigh = naverRepositoryImpl.BetweenReviewHigh(review);                                   // 1. naverRepositoryImpl 구현
        model.addAttribute("searchList", BetweenReviewHigh);
    }

    /*
      기능 : 가게 리뷰가 n개이하인 가게만 보여주는 기능
      작성자 : 이상훈
      작성일자 : 22.1.10
      수정일자 : 22.1.11
    */
    public void BetweenReviewLow(Model model, int review) {
        List<Naver> BetweenReviewLow = naverRepositoryImpl.BetweenReviewLow(review);                                    // 1. naverRepositoryImpl 구현
        model.addAttribute("searchList", BetweenReviewLow);
    }
}

