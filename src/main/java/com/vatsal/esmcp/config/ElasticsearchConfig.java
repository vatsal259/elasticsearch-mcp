package com.vatsal.esmcp.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    // 🔐 Elasticsearch API Key (Base64 encoded)
    private static final String API_KEY =
            "VmY1dFlwd0JzbnJ3RGpmUzFxQ0U6azlPVFJXV3ZoUzhsOXgtc1FvZkVjUQ==";

    @Bean
    public ElasticsearchClient elasticsearchClient() {

        RestClient restClient = RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Authorization", "ApiKey " + API_KEY)
                })
                .build();

        ElasticsearchTransport transport =
                new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(transport);
    }
}
