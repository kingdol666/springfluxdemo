package com.example.webfluxdemo.controller;

import com.example.webfluxdemo.excutor.Excutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/flux")
public class WebFluxController {
    @Autowired
    private Excutor excutor;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping(path = "/helloFlux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> helloFlux() {
        Flux<String> dataFlux = Flux.generate(
                () -> 0, // 初始状态
                (state, sink) -> {
                    // 生成数据
                    String value = generateData(state);

                    // 将数据发送给下游
                    sink.next(value);

                    // 更新状态
                    int newState = state + 1;

                    // 判断是否继续生成数据
                    if (shouldContinue(newState)) {
                        // 继续生成
                        return newState;
                    } else {
                        // 停止生成
                        sink.complete();
                        return newState;
                    }
                }
        );

        // 订阅数据流
        dataFlux.subscribe(System.out::println);
        return dataFlux.delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(path = "/generateDataFlux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> generateDataFlux() {
        // generate,有返回值,且sink.next的返回值决定了是否继续生成数据
        // 返回的值就是state的新流, sink.next(state) 即新的流的生成
        Flux<Integer> numberFlux = Flux.generate(
                () -> 0, // 初始状态
                (state, sink) -> {
                    // 生成数据
                    int value = state;
                    // 将数据发送给下游
                    sink.next(value);
                    // 更新状态
                    int newState = state + 1;
                    // 判断是否继续生成数据
                    if (newState <= 10) {
                        // 继续生成
                    } else {
                        // 停止生成
                        sink.complete();
                    }
                    return newState;
                }
        );
        return numberFlux.delayElements(Duration.ofMillis(100));
    }

    @GetMapping(path = "/generateData1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateData1(@RequestParam("multiplier") int multiplier) {
//        int multiplier = 2; // 声明要传入的变量
        // create方法创建数据流,没有返回值,且sink.next仅仅传给下层数据流
        Flux<Integer> dataFlux = Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                // 生成数据
                int value = generateData1(i, multiplier);
                // 将数据发送给下游
                sink.next(value);
            }
            // 完成数据生成,自动生成出Flux<>对象
            sink.complete();
        });
        Flux<String> map = dataFlux.filter(i -> i % 2 == 0).map(v -> "hehe" + v * 2);
        // flatMap 对流进行两次不同的映射方案并且拼接为一个新的流
        Flux<String> newMap = map.flatMap(s -> Flux.just(s.toUpperCase(), s.toLowerCase()));
        return newMap.delayElements(Duration.ofMillis(100));
    }

    @GetMapping(path = "/generateData2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateData2(@RequestParam("str") String str) throws ExecutionException, InterruptedException {
        int a = 1;
        Flux<String> flux = Flux.just("hello");
        excutor.executorService.submit(()-> System.out.println(a));
        class MyThread implements Callable<String>{
            final String str;
            public MyThread(String str) {
                this.str = str;
            }
            @Override
            public String call() throws Exception {
                String name = Thread.currentThread().getName();
                System.out.println("name = " + name);
                System.out.println(str);
                return "haha"+" "+ str;
            }
        }
        Future<String> res = excutor.executorService.submit(new MyThread(str));
        String s = res.get();
        String[] s1 = s.split(" ");
        for (var st : s1){
            flux = flux.concatWith(Flux.just(st));
        }
        return flux.delayElements(Duration.ofMillis(100));
    }

    private static String generateData(int state) {
        // 生成数据的逻辑，可以根据状态来生成不同的数据
        return "Data " + state;
    }
    private static int generateData1(int index, int multiplier) {
        // 生成数据的逻辑
        return index * multiplier;
    }
    private static boolean shouldContinue(int state) {
        // 判断是否继续生成数据的逻辑，可以根据状态来决定是否继续生成
        return state < 5; // 在状态小于5时继续生成数据
    }
}
