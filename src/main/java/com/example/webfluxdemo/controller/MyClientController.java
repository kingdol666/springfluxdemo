package com.example.webfluxdemo.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class MyClientController {

        private final CloseableHttpClient httpClient;

        @Autowired
        public MyClientController(CloseableHttpClient httpClient) {
                this.httpClient = httpClient;
        }

        @GetMapping("/example")
        public String exampleEndpoint() throws IOException {
                String url = "http://localhost:8081/api/test";

                HttpGet request = new HttpGet(url);

                // 发送请求并获取响应
                HttpResponse response = httpClient.execute(request);
                HttpEntity entity = response.getEntity();

                // 处理响应
                String responseBody = "";
                if (entity != null) {
                        responseBody = EntityUtils.toString(entity);
                }

                // 关闭连接
                EntityUtils.consume(entity);

                return responseBody;
        }

        @GetMapping("/test")
        public String testEndpoint() {
                return "test";
        }
}