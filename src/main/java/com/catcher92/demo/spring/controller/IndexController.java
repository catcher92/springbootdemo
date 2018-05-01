package com.catcher92.demo.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author catcher92
 * @date 2018/5/1
 */
@RestController
@Slf4j
public class IndexController {

    @RequestMapping("/hello1")
    public String hello1(String name) {
        log.info("hello1 start");
        // controller线程会被一直占用
        String str = sayHello(name);
        log.info("hello1 end");
        return str;
    }

    @RequestMapping("/hello2")
    public Mono<String> hello2(String name) {
        log.info("hello2 start");
        // stream是惰性求职controller线程不会被阻塞
        Mono<String> result = Mono.fromSupplier(() -> sayHello(name));
        log.info("hello2 end");
        return result;
    }

    @RequestMapping(value = "/hello3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> hello3(String name) {
        log.info("hello3 start");
        Flux<String> stringFlux = Flux.fromStream(IntStream.rangeClosed(1, 5).mapToObj(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "flux-" + name + "-" + i;
        }));
        log.info("hello3 end");
        return stringFlux;
    }

    private String sayHello(String name) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format("Hello %s!", name);
    }
}
