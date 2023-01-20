package com.example.pirate99_final.review.service;

import com.example.pirate99_final.review.dto.ReviewRequestDto;
import com.example.pirate99_final.review.entity.Review;
import com.example.pirate99_final.review.repository.ReviewJdbcRepository;
import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.store.repository.StoreRepository;
import com.example.pirate99_final.user.entity.User;
import com.example.pirate99_final.user.repository.UserRepository;
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
    private final RedisTemplate<String, ReviewRequestDto> reviewRedisTemplate;
    private final ReviewJdbcRepository reviewJdbcRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    // 10분 마다 Chatting data Redis -> MySQL
    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void writeBack() {
        log.info("Scheduling start");

        BoundSetOperations<String, ReviewRequestDto> setOperations = reviewRedisTemplate.boundSetOps("reviewIdx");

        ScanOptions scanOptions = ScanOptions.scanOptions().build();

        List<Review> reviewList = new ArrayList<>();

            try (Cursor<ReviewRequestDto> cursor = setOperations.scan(scanOptions)) {
                while (cursor.hasNext()) {
                    ReviewRequestDto reviewRequestDto = cursor.next();

                    Store store = storeRepository
                            .findById(reviewRequestDto.getStoreid()).orElse(null);

                    User user = userRepository
                            .findByUsername(reviewRequestDto.getUsername()).orElse(null);

                    reviewList.add(Review.of(reviewRequestDto, store, user));
                }
                reviewJdbcRepository.batchInsertReviewList(reviewList);

                reviewJdbcRepository.batchUpdateReviewCnt(reviewList);

                reviewJdbcRepository.batchUpdateReviewStarScore(reviewList);

            redisTemplate.delete("reviewIdx");

            } catch (Exception e) {
//            e.printStackTrace();
                log.error("An exception occurred while trying to write back reviews from Redis to MySQL", e);
            }
            log.info("Scheduling done");
        }
    }
