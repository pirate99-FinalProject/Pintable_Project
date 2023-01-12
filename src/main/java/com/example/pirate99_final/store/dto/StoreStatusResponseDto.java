package com.example.pirate99_final.store.dto;

import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.store.entity.StoreStatus;
import lombok.Getter;

@Getter
public class StoreStatusResponseDto {
    private Long storeStatusId;         // storeStatusId
    private int  totalTableCnt;         // 총 테이블 수
    private int  availableTableCnt;       // 현재 사용 중인 테이블 수
    private int  waitingCnt;            // 대기 팀 수
    private Long storeId;               // 상호 ID

    public StoreStatusResponseDto(StoreStatus storestatus){
        this.storeStatusId = storestatus.getStoreStatusId();
        this.totalTableCnt = storestatus.getTotalTableCnt();
        this.availableTableCnt = storestatus.getAvailableTableCnt();
        this.waitingCnt = storestatus.getWaitingCnt();
        this.storeId = storestatus.getStore().getStoreId();
    }
}
