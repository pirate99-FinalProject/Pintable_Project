package com.example.pirate99_final.map.service;

import com.example.pirate99_final.map.entity.Naver;
import com.example.pirate99_final.map.repository.NaverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NaverService {
    private final NaverRepository naverRepository;

    public void searchMap(Model model, String storeName) {

        String storeNameTrim = storeName.replaceAll(" ", "");

        List<Naver> naverList = naverRepository.findByStorenameContaining(storeNameTrim);

        for (int i = 0 ; i < naverList.size(); i++) {
            model.addAttribute("Lat"+i, naverList.get(i).getXcoordinate());
            model.addAttribute("Lng"+i, naverList.get(i).getYcoordinate());
            model.addAttribute("storeName"+i, naverList.get(i).getStorename());
            model.addAttribute("placeAddress"+i, naverList.get(i).getRoadnameaddress());
        }
    }
}