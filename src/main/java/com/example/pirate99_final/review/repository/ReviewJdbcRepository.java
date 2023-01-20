package com.example.pirate99_final.review.repository;


import com.example.pirate99_final.review.entity.Review;
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

    // Redis Cache -> Review Table Insert
    public void batchInsertReviewList(List<Review> reviewList){

        String sql = "INSERT INTO review" + "(content,star_score,storeid,userid) VALUE(?,?,?,?)";


        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Review review = reviewList.get(i);
                ps.setString(1,review.getContent());
                ps.setDouble(2,review.getStarScore());
                ps.setLong(3,review.getStore().getStoreId());
                ps.setLong(4,review.getUser().getUserId());
            }

            @Override
            public int getBatchSize() {
                return reviewList.size();
            }
        });
    }

    // Store Table review_cnt Update
    public void batchUpdateReviewCnt(List<Review> reviewList){

        String sql = "UPDATE store SET review_cnt = review_cnt + 1 where store_id = " + "(?)";


        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Review review = reviewList.get(i);
                ps.setLong(1,review.getStore().getStoreId());
            }

            @Override
            public int getBatchSize() {
                return reviewList.size();
            }
        });
    }

    // Store Table star_score Update
    public void batchUpdateReviewStarScore(List<Review> reviewList){

        String sql = "UPDATE store SET star_score = TRUNCATE (((((review_cnt - 1) * star_score) + (?)) / review_cnt), 2) where store_id = " + "(?)";


        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Review review = reviewList.get(i);
                ps.setDouble(1, review.getStarScore());
                ps.setLong(2,review.getStore().getStoreId());
            }

            @Override
            public int getBatchSize() {
                return reviewList.size();
            }
        });
    }
}