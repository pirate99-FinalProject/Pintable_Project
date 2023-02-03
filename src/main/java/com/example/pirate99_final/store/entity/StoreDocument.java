package com.example.pirate99_final.store.entity;

import com.example.pirate99_final.global.entity.TimeStamped;
import com.example.pirate99_final.store.dto.StoreRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.*;

//import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
//@Entity
@Document(indexName = "es-test-store-v3.0.2")
@Mapping(mappingPath = "elastic/store-mapping.json")
@Setting(settingPath = "elastic/store-setting.json")
public class StoreDocument extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "store_id", nullable = false)
    private Long id;

//    private Long store_id;                                              // 가게 ID

    private String address;                                             // 주소

    @Field(type = FieldType.Keyword, name = "road_name_address")
    private String roadNameAddress;                                     // 도로명주소

    private int post_number;                                            // 우편번호

    @Field(type = FieldType.Text, name = "store_name")
    private String storeName;                                           // 가게이름

    @Field(type = FieldType.Keyword, name = "type_of_business")
    private String typeOfBusiness;                                      // 업종

    private double x_coordinate;                                         // X좌표

    private double y_coordinate;                                         // Y좌표

    @Field(type = FieldType.Keyword, name = "star_score")
    private double starScore;                                           // 별점

    @Field(type = FieldType.Keyword, name = "review_cnt")
    private int reviewCnt;                                              // 리뷰

    public StoreDocument(StoreRequestDto requestDto){
        this.address            =   requestDto.getAddress();            // 주소
        this.roadNameAddress    =   requestDto.getRoadNameAddress();    // 도로명주소
        this.post_number        =   requestDto.getPostNumber();         // 우편번호
        this.storeName          =   requestDto.getStoreName();          // 상호명
        this.typeOfBusiness     =   requestDto.getTypeOfBusiness();     // 업종명
        this.x_coordinate       =   requestDto.getXcoordinate();        // X좌표
        this.y_coordinate       =   requestDto.getYcoordinate();        // Y좌표
        this.starScore          =   requestDto.getStarScore();          // 별점
        this.reviewCnt          =   requestDto.getReviewCnt();          // 리뷰수
    }

}
