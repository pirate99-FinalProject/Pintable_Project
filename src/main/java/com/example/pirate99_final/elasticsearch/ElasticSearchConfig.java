package com.example.pirate99_final.elasticsearch;

import com.example.pirate99_final.store.repository.StoreSearchRepository;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses = StoreSearchRepository.class)
//@EnableElasticsearchRepositories(basePackageClasses = {StoreSearchRepository.class, StoreSearchRepositoryImpl.class})
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private String port;

    @Value("${elasticsearch.user_name}")
    private String userName;

    @Value("${elasticsearch.user_password}")
    private String userPassword;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(host+":" + port)
                .withBasicAuth(userName,userPassword)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}