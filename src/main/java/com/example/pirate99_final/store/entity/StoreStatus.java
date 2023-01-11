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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int totalTableCnt;

    @Column(nullable = false)
    private int availableTableCnt;

    @Column(nullable = false)
    private int waitingCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = false)

    private Store store;

    public StoreStatus(Store store){
        this.totalTableCnt      =   40;
        this.availableTableCnt  =   40;
        this.waitingCnt         =   0;
        this.store              =   store;
    }

    public void update(int availableTableCnt){
        this.availableTableCnt  =   availableTableCnt;
    }

    public void update_waitingcnt(int waitingCnt){
        this.waitingCnt = waitingCnt;
    }
}