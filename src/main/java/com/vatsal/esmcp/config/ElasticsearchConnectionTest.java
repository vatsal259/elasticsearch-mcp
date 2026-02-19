package com.vatsal.esmcp.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.InfoResponse;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchConnectionTest {

    private final ElasticsearchClient elasticsearchClient;

    public ElasticsearchConnectionTest(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testConnection() {
        try {
            InfoResponse info = elasticsearchClient.info();
        } catch (Exception e) {
        }
    }
}
