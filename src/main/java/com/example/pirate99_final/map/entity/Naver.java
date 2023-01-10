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
    private String address;
    private String roadnameaddress;     // 가게 주소
    private int postnumber;     // 가게 주소
    private String storename;           // 가게 이름
    private String typeofbusiness;           // 가게 이름
    private double xcoordinate;         // 위도
    private double ycoordinate;         // 경도
    private double starscore;
    private int reviewcnt;


    public Naver(double xcoordinate, double ycoordinate, String storename,String roadnameaddress) {
        this.xcoordinate = xcoordinate;
        this.ycoordinate = ycoordinate;
        this.storename = storename;
        this.roadnameaddress = roadnameaddress;
    }
}
