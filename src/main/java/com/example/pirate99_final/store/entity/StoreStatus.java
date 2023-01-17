package com.example.pirate99_final.store.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class StoreStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeStatusId;

    @Column(nullable = false)
    private int totalTableCnt;

    @Column(nullable = false)
    private int availableTableCnt;

    @Column(nullable = false)
    private int waitingCnt;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;


    @Column(nullable = false)
    private int limitWaitingCnt;


    public StoreStatus(Store store){
        this.totalTableCnt      =   40;
        this.availableTableCnt  =   40;
        this.waitingCnt         =   0;
        this.store              =   store;
        this.limitWaitingCnt    =   0;
    }
    public void update(int availableTableCnt){
        this.availableTableCnt  =   availableTableCnt;
    }

    public void update_waitingCnt(int waitingCnt, int availableTableCnt){

        this.waitingCnt = waitingCnt;
        this.availableTableCnt = availableTableCnt;
    }

    public void update_limitWaitingCnt(int limitWaitingCnt) {
        this.limitWaitingCnt = limitWaitingCnt;
    }
}