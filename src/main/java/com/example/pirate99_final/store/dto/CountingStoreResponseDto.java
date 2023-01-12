package com.example.pirate99_final.store.dto;

import lombok.Getter;
import net.bytebuddy.asm.Advice;

@Getter
public class CountingStoreResponseDto {
    int availableTableCnt;

    public CountingStoreResponseDto(int availableTableCnt){
        this.availableTableCnt  =   availableTableCnt;
    }
}
