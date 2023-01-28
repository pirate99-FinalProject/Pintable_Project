package com.example.pirate99_final.store.dto;

import com.example.pirate99_final.store.entity.Store;
import lombok.Getter;

@Getter
public class StoreResponseDto {

    private String storeName;

    private int numberOfTeamsWaiting;

    private int numberOfCustomersInUse;



    public StoreResponseDto(Store store, int numberOfTeamsWaiting, int numberOfCustomersInUse) {

        this.storeName = store.getStoreName();
        this.numberOfTeamsWaiting = numberOfTeamsWaiting;
        this.numberOfCustomersInUse = numberOfCustomersInUse;
    }
}
