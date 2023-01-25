package com.example.pirate99_final.review.service;

import com.example.pirate99_final.review.dto.RedisRequestDto;
import com.example.pirate99_final.review.repository.ReviewJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewWriteBackScheduling {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, RedisRequestDto> reviewRedisTemplate;
    private final ReviewJdbcRepository reviewJdbcRepository;

    // 10분 마다 Chatting data Redis -> MySQL
    @Scheduled(cron = "0 0/10 * * * ?")
    @Transactional
    public void writeBack() {
        log.info("Scheduling start");

        BoundSetOperations<String, RedisRequestDto> setOperations = reviewRedisTemplate.boundSetOps("reviewIdx");

        ScanOptions scanOptions = ScanOptions.scanOptions().build();

        List<RedisRequestDto> reviewList = new ArrayList<>();

        try (Cursor<RedisRequestDto> cursor = setOperations.scan(scanOptions)) {
            while (cursor.hasNext()) {
                RedisRequestDto redisRequestDto = cursor.next();

                reviewList.add(redisRequestDto);
            }
            reviewJdbcRepository.batchUpdateReviewStarScore(reviewList);

            redisTemplate.delete("reviewIdx");

        } catch (Exception e) {
            log.error("An exception occurred while trying to write back reviews from Redis to MySQL", e);
        }
        log.info("Scheduling done");
    }
}
