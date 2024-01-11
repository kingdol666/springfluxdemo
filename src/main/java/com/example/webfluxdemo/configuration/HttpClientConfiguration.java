package com.example.webfluxdemo.configuration;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class HttpClientConfiguration {

    @Bean
    public CloseableHttpClient httpClient() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        // 设置请求头
        httpClientBuilder.setDefaultHeaders(Arrays.asList(createDefaultHeaders()));

        // 设置基础配置
        httpClientBuilder.setDefaultRequestConfig(createRequestConfig());

        // 创建HttpClient对象
        CloseableHttpClient httpClient = httpClientBuilder.build();
        return httpClient;
    }

    private RequestConfig createRequestConfig() {
        // 设置连接超时时间
        int connectionTimeout = 5000;
        // 设置读取超时时间
        int socketTimeout = 5000;

        return RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
    }

    private Header[] createDefaultHeaders() {
        Header[] headers = {
                new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
                // 可以添加其他请求头
        };
        return headers;
    }
}