package com.shanhaihen.rabbitdemo.hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * queuesToDeclare 沒有则创建
 * 默认创建 持久化 非独占 不自动删除队列
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(value = "hello", durable = "true", autoDelete = "false"))
public class SimpleQueueConsume {

    @RabbitHandler
    public void receive(String message) {
        System.out.println("message==:" + message);
    }
}
