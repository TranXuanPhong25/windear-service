package com.windear.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    @Primary
    @Qualifier("goodreadsWebClient")
    public WebClient goodreadsWebClient(@Value("${goodreads.api.url}") String apiUrl, @Value("${goodreads.api.key}") String apiKey) {
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("X-api-key", apiKey)
                .exchangeStrategies(strategies)
                .build();
    }

    @Bean
    @Qualifier("auth0WebClient")
    public WebClient auth0WebClient(@Value("${management.api.base.url}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}