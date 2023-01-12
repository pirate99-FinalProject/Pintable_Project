package com.example.pirate99_final.waiting.repository;


import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.store.entity.StoreStatus;
import com.example.pirate99_final.user.entity.User;
import com.example.pirate99_final.waiting.entity.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WaitingRepository  extends JpaRepository<Waiting, Long> {

    List<Waiting> findAllByOrderByCreatedAtDesc();
    Waiting findByWaitingId(Long waitingId);

    // find StoreStatus,
    List<Waiting> findAllByStoreStatusAndWaitingStatusAndOrderByWaitingIdAtDesc(StoreStatus storeStatus, int waitingStatus);

    Waiting findByUser(User user);

}
