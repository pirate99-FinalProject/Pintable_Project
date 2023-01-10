package com.example.pirate99_final.review.entity;

import com.example.pirate99_final.review.dto.ReviewRequestDto;
import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private double starScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private User user;

    public Review(ReviewRequestDto requestDto){
        this.content    =   requestDto.getContent();
        this.starScore  =   requestDto.getStarScore();
    }


}
