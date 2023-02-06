package com.example.pirate99_final.waiting.repository;

import com.example.pirate99_final.store.entity.StoreStatus;
import com.example.pirate99_final.user.entity.User;
import com.example.pirate99_final.waiting.entity.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface WaitingRepository  extends JpaRepository<Waiting, Long> {

    Waiting findByWaitingId(Long waitingId);
    List<Waiting> findAllByStoreStatusAndWaitingStatusOrWaitingStatusOrderByWaitingIdAsc(StoreStatus storeStatus, int waitingStatus, int waitingStatus2 );

    @Query(value = "select * from waiting where (waiting_status = :waitingStatus1 or waiting_status = :waitingStatus2) and store_status_id = :storeStatusId", nativeQuery = true)
    List<Waiting> waitingList(int waitingStatus1, int waitingStatus2, Long storeStatusId);

    Waiting findByStoreStatusAndUser(StoreStatus storestatus, User user);

    @Query(value = "select *from waiting where (waiting_status = :waitingStatus1 or waiting_status = :waitingStatus2) and store_status_id = :storeStatusId and user_id = :userId", nativeQuery = true)
    Optional<Waiting> alreadyQueue(int waitingStatus1, int waitingStatus2, Long userId, Long storeStatusId);

    List<Waiting> findAllByStoreStatusOrderByWaitingIdAsc(StoreStatus storeStatus);

}
