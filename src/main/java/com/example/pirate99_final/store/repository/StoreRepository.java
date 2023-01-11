package com.example.pirate99_final.store.repository;

import com.example.pirate99_final.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAllBy();
}
