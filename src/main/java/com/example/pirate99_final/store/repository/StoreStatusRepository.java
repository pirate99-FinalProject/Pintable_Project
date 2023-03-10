package com.example.pirate99_final.store.repository;

import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.store.entity.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface StoreStatusRepository extends JpaRepository<StoreStatus, Long> {
    List<StoreStatus> findAllBy();
    StoreStatus findByStore(Store store);

    @Transactional
    void deleteByStore(Store store);

    @Query(value = "select * from store_status as a where a.store_id = :storeId",nativeQuery = true)
    StoreStatus findByStoreId(Long storeId);
}
