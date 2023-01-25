package com.example.pirate99_final.review.repository;

import com.example.pirate99_final.review.dto.RedisRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void batchUpdateReviewStarScore(List<RedisRequestDto> reviewList) {

        String sqlReviewCntUpdate = "UPDATE store a SET a.review_cnt = ( SELECT count(*) FROM review where storeid=(?))," +
                "a.star_score = TRUNCATE((select avg(star_score) from review where storeid=(?)),2) WHERE store_id=" + "(?)";

        jdbcTemplate.batchUpdate(sqlReviewCntUpdate, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                RedisRequestDto redisRequestDto = reviewList.get(i);
                ps.setLong(1, redisRequestDto.getStoreid());
                ps.setLong(2, redisRequestDto.getStoreid());
                ps.setLong(3, redisRequestDto.getStoreid());
            }

            @Override
            public int getBatchSize() {
                return reviewList.size();
            }
        });
    }

//    public void batchUpdateReviewStarScore1(List<RedisRequestDto> reviewList) {
//
//        String sqlReviewCntUpdate1 = "UPDATE store a SET a.review_cnt = review_cnt + 1, a.star_score = TRUNCATE (((((review_cnt - 1) * star_score) + (?)) / review_cnt), 2) WHERE store_id=" + "(?)";
//
//        jdbcTemplate.batchUpdate(sqlReviewCntUpdate1, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                RedisRequestDto redisRequestDto = reviewList.get(i);
//                ps.setLong(1, redisRequestDto.getStoreid());
//                ps.setLong(2, redisRequestDto.getStoreid());
//                ps.setLong(3, redisRequestDto.getStoreid());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return reviewList.size();
//            }
//        });
//    }
}