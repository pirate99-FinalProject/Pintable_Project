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
    private final NaverRepository naverRepository;
    private final NaverRepositoryImpl naverRepositoryImpl;

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
}


