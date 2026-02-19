package com.vatsal.esmcp.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ElasticsearchClient client;

    public ProductService(ElasticsearchClient client) {
        this.client = client;
    }

    public List<String> searchProducts(String query) {
        // simplified demo logic
        return List.of(
                "Product matching: " + query,
                "Another product matching: " + query
        );
    }
}
