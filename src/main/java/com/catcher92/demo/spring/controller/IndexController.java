package com.catcher92.demo.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author catcher92
 * @date 2018/5/1
 */
@RestController
public class IndexController {

    @RequestMapping("/hello1")
    public String hello1(String name) {
        return sayHello(name);
    }

    @RequestMapping("/hello2")
    public Mono<String> hello2(String name) {
        return Mono.just(sayHello(name));
    }

    private String sayHello(String name) {
        return String.format("Hello %s!", name);
    }
}
