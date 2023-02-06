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

        String sql = "UPDATE store a SET a.review_cnt = (SELECT review_cnt FROM reviewCntView WHERE storeid=(?)),"+
                     "a.star_score = (SELECT star_score FROM starStoreView WHERE storeid=(?)), a.modified_at = now()"+
                     "WHERE store_id=(?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
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
}