package com.example.pirate99_final.map.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Naver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String storeName;    // 가게 이름
    private String placeAddress; // 가게 주소
    private double Lat;          // 위도
    private double Lng;          // 경도
}
