package com.catcher92.demo.spring.controller;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/webSocket/{userId}")
@Component
public class WebSocketDemo {

    private static final ConcurrentHashMap<Integer, Session> sessionPools = new ConcurrentHashMap<>();

    private void sendMessage(Integer userId, String message) throws IOException {
        final Session session = sessionPools.get(userId);
        if (session != null) {
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") Integer userId) {
        sessionPools.put(userId, session);
        System.out.println(userId + "加入webSocket!");
        try {
            sendMessage(userId, "欢迎" + userId + "加入连接！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(@PathParam(value = "userId") Integer userId) {
        sessionPools.remove(userId);
        System.out.println(userId + "断开webSocket连接！");
    }

    @OnMessage
    public void onMessage(@PathParam(value = "userId") Integer userId, String message) throws IOException {
        message = "客户端 " + userId + " 说:" + message;
        System.out.println(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }
}
