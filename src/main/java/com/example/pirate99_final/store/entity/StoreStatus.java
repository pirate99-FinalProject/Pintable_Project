package com.example.pirate99_final.store.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class StoreStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String totalTableCnt;

    @Column(nullable = false)
    private String availableTableCnt;

    @Column(nullable = false)
    private String waitingCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeid", nullable = false)
    private Store store;


}
