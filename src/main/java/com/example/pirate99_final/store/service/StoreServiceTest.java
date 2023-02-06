package com.example.pirate99_final.store.service;

import com.example.pirate99_final.global.exception.CustomException;
import com.example.pirate99_final.global.exception.ErrorCode;
import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.store.entity.StoreStatus;
import com.example.pirate99_final.store.repository.StoreRepository;
import com.example.pirate99_final.store.repository.StoreStatusRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.jupiter.api.Assertions.*;

//@AutoConfigureMockMvc
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class StoreServiceTest {

    private final StoreService storeService;
    private final StoreRepository storeRepository;                      // store repo connect
    private final StoreStatusRepository storeStatusRepository;          // storeStatus repo connect

    @Test
    void 입장테스트() throws InterruptedException {
        int threadCount = 40;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for(int i = 0; i<threadCount; i++){
            executorService.submit(()->{
               try{
                storeService.enterStore(1L);
            }finally{
                   latch.countDown();
            }
            });
        }

        latch.await();

        // 1. find store
        Store store = storeRepository.findById(1L).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        // 2. storeStatus check
        StoreStatus storeStatus = storeStatusRepository.findByStore(store);
        assertEquals(15, storeStatus.getAvailableTableCnt());


        System.out.println("총 테이블 수 " + storeStatus.getTotalTableCnt());
        System.out.println("테이블 제한 수 " + storeStatus.getLimitWaitingCnt());
        System.out.println("웨이팅 수 " + storeStatus.getWaitingCnt());
        System.out.println("이용가능 테이블 수 " + storeStatus.getAvailableTableCnt());
    }
}