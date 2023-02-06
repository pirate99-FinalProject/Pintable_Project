package com.example.pirate99_final;

import com.example.pirate99_final.store.repository.StoreRepository;
import com.example.pirate99_final.store.repository.StoreSearchRepository;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = StoreSearchRepository.class))
//@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
//        type = FilterType.ASSIGNABLE_TYPE,
//        classes = {StoreSearchRepository.class, StoreSearchRepositoryImpl.class, ElasticsearchRepository.class}))
@SpringBootApplication
@EnableScheduling
@EnableBatchProcessing
@EnableJpaAuditing
@EnableCaching
public class Pirate99FinalApplication {
    public static void main(String[] args) {
        SpringApplication.run(Pirate99FinalApplication.class, args);
    }
}
