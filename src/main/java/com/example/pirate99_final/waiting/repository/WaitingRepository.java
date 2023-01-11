package com.example.pirate99_final.waiting.repository;

import com.example.pirate99_final.waiting.entity.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingRepository  extends JpaRepository<Waiting, Long> {

    
}
