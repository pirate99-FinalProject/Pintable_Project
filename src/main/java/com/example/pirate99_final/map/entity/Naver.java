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
    private String roadnameaddress;
    private int postnumber;
    private String storename;
    private String typeofbusiness;
    private double xcoordinate;
    private double ycoordinate;
    private double starscore;
    private int reviewcnt;
}
