package com.example.webfluxdemo.excutor;

import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Excutor {
    public ExecutorService executorService = Executors.newFixedThreadPool(10);
}
