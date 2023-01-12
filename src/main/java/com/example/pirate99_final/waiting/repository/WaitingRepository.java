package com.example.pirate99_final.waiting.repository;


import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.store.entity.StoreStatus;
import com.example.pirate99_final.user.entity.User;
import com.example.pirate99_final.waiting.entity.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WaitingRepository  extends JpaRepository<Waiting, Long> {

    Waiting findByWaitingId(Long waitingId);

    List<Waiting> findAllByWaitingStatusOrWaitingStatus(int waitingStatus, int waitingStatus2);

    Waiting findByUser(User user);
}
