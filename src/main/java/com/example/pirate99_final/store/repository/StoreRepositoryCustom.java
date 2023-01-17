package com.example.pirate99_final.store.repository;

import com.example.pirate99_final.store.config.SearchCondition;
import com.example.pirate99_final.store.entity.Store;

import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> DynamicSQL(SearchCondition condition, String select);
}
