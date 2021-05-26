package com.catcher92.demo.spring.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("emitter")
public class EmitterController {

    @GetMapping("test")
    @ResponseBody
    public ResponseEntity<ResponseBodyEmitter> emitter() {
        final ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        new Thread(new Runnable() {
            public void run() {
                try {
                    // emitter.send(Collections.singletonMap("event", "开始检测hdfs"));
                    emitter.send("开始检测hdfs");
                    TimeUnit.SECONDS.sleep(3);
                    // emitter.send(Collections.singletonMap("event", "完成检测hdfs"));
                    emitter.send("完成检测hdfs");

                    // emitter.send(Collections.singletonMap("event", "开始检测yarn"));
                    emitter.send("开始检测yarn");
                    TimeUnit.SECONDS.sleep(3);
                    // emitter.send(Collections.singletonMap("event", "完成检测yarn"));
                    emitter.send("完成检测yarn");

                    emitter.complete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(emitter);
    }

}
