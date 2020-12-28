package com.shanhaihen.rabbitdemo.hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 工作队列的消费者 会平均分配消息
 */
@Component
public class WorkQueueConsume {

    @RabbitListener(queuesToDeclare = @Queue("spring_work1"))
    public void receive(String message) {
        System.out.println("receive1 message==:" + message);
    }

    @RabbitListener(queuesToDeclare = @Queue("spring_work1"))
    public void receive2(String message) {
        System.out.println("receive2  spring_work message==:" + message);
    }
}
