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
            System.out.println("========================================");
            System.out.println("✅ Successfully connected to Elasticsearch!");
            System.out.println("Cluster Name: " + info.clusterName());
            System.out.println("Version: " + info.version().number());
            System.out.println("Cluster UUID: " + info.clusterUuid());
            System.out.println("========================================");
        } catch (Exception e) {
            System.err.println("========================================");
            System.err.println("❌ Failed to connect to Elasticsearch");
            System.err.println("Error: " + e.getMessage());
            System.err.println("========================================");
        }
    }
}
