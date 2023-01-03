package com.example.pirate99_final.map.repository;

import com.example.pirate99_final.map.entity.Naver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NaverRepository extends JpaRepository<Naver, Long> {
    // 가게 이름을 기준으로 찾음
    Optional<Naver> findByStoreName(String storeName);
}
