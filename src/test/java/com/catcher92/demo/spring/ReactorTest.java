package com.catcher92.demo.spring;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author catcher92
 * @date 2018/5/1
 */
public class ReactorTest {

    @Test
    public void testReactor() {
        // reactor = jdk8 stream + jdk9 reactive stream
        // Mono是0-1个元素
        // Flux是0-N个元素
        String[] strs = {"1" , "2", "3", "4", "5"};
        // 订阅者
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {

            private Subscription subscription;
            // 需要的数据条数
            private final int maxRequest = 3;
            // 当前已经请求的条数
            private final AtomicInteger requestTimes = new AtomicInteger();

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe");
                // 保存订阅关系。需要用它来给发布者响应
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                // 消费数据
                System.out.println("onNext:" + integer);
                // 确定是否需要继续请求数据
                if (requestTimes.incrementAndGet() < maxRequest) {
                    subscription.request(1);
                } else {
                    subscription.cancel();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError");
                subscription.cancel();
            }

            @Override
            public void onComplete() {
                // 发布者的所有数据都订阅完才会执行onComplete方法
                System.out.println("onComplete");
                subscription.cancel();
            }
        };
        // 这里是jdk8的stream
        Flux.fromArray(strs).map(Integer::parseInt)
        // 最终操作，这里是jdk9的reactiveStream(此处使用的)
        .subscribe(subscriber);
    }
}
