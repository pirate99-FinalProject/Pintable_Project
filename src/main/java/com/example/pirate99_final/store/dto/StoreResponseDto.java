package com.example.pirate99_final.store.dto;

import com.example.pirate99_final.store.entity.Store;
import lombok.Getter;

@Getter
public class StoreResponseDto {
    private Long storeStatusId;
    private int totalTableCnt;
    private int currentTableCnt;
    private int waitingCnt;
    private Long storeId;

    public StoreResponseDto(Store store){

    }
}
