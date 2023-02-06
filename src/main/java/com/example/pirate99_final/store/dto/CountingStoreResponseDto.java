package com.example.pirate99_final.store.dto;

import lombok.Getter;

@Getter
public class CountingStoreResponseDto {
    int availableTableCnt;

    public CountingStoreResponseDto(int availableTableCnt){
        this.availableTableCnt  =   availableTableCnt;
    }
}
