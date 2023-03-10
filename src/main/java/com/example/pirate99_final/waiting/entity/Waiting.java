package com.example.pirate99_final.waiting.entity;

import com.example.pirate99_final.global.entity.TimeStamped;
import com.example.pirate99_final.store.entity.StoreStatus;
import com.example.pirate99_final.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor

public class Waiting extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long waitingId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "storeStatusId")
    private StoreStatus storeStatus;

    @Column
    private int waitingStatus;

    public Waiting(User user, StoreStatus storeStatus, int waitingStatus) {
        this.user = user;
        this.storeStatus = storeStatus;
        this.waitingStatus = waitingStatus;
    }

    public void update(int waitingStatus) {
        this.waitingStatus = waitingStatus;

    }
}
