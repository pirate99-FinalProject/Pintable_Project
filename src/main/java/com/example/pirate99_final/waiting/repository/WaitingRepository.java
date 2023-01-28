package com.example.pirate99_final.waiting.repository;


import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.store.entity.StoreStatus;
import com.example.pirate99_final.user.entity.User;
import com.example.pirate99_final.waiting.dto.WaitingRequestDto;
import com.example.pirate99_final.waiting.entity.Waiting;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WaitingRepository  extends JpaRepository<Waiting, Long> {

    Waiting findByWaitingId(Long waitingId);

    List<Waiting> findAllByWaitingStatusOrWaitingStatus(int waitingStatus, int waitingStatus2);

    List<Waiting> findAllByStoreStatusAndWaitingStatusOrWaitingStatusOrderByWaitingIdAsc(StoreStatus storeStatus, int waitingStatus, int waitingStatus2 );

    Waiting findByStoreStatusAndUser(StoreStatus storestatus, User user);

    @Query(value = "select *from waiting where (waiting_status = :waitingStatus1 or waiting_status = :waitingStatus2) and store_status_id = :storeId and user_id = :userId", nativeQuery = true)
    Optional<Waiting> alreadyQueue(int waitingStatus1, int waitingStatus2, Long userId, Long storeId);

    List<Waiting> findAllByStoreStatusOrderByWaitingIdAsc(StoreStatus storeStatus);
}
