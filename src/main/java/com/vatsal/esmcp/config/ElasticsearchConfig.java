package com.vatsal.esmcp.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.vatsal.esmcp.repository")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris:http://localhost:9200}")
    private String elasticsearchUrl;

    @Value("${spring.elasticsearch.api-key:}")
    private String apiKey;

    @Value("${spring.elasticsearch.username:}")
    private String username;

    @Value("${spring.elasticsearch.password:}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        ClientConfiguration.MaybeSecureClientConfigurationBuilder builder =
                ClientConfiguration.builder()
                        .connectedTo(elasticsearchUrl.replace("http://", "").replace("https://", ""));

        if (apiKey != null && !apiKey.isEmpty()) {
            System.out.println("Using API Key authentication");
            builder.withHeaders(() -> {
                org.springframework.data.elasticsearch.support.HttpHeaders headers = new
                        org.springframework.data.elasticsearch.support.HttpHeaders();
                headers.add("Authorization", "ApiKey " + apiKey);
                return headers;
            });
        } else if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            System.out.println("Using Username/Password authentication");
            builder.withBasicAuth(username, password);
        } else {
            System.out.println("Using no authentication");
        }

        return builder.build();
    }
}
