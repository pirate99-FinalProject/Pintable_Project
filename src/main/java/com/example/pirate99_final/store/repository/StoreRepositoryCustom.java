package com.example.pirate99_final.store.repository;

import com.example.pirate99_final.store.config.SearchCondition;
import com.example.pirate99_final.store.dto.QuerydslDto;

import java.util.List;

public interface StoreRepositoryCustom {
    List<QuerydslDto> DynamicSQL(SearchCondition condition, String select);
}
