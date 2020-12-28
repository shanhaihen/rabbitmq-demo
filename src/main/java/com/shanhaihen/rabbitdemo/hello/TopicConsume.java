package com.shanhaihen.rabbitdemo.hello;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * # 代表任意多个
 * * 代表一个
 */
@Component
public class TopicConsume {
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//创建临时队列
                    exchange = @Exchange(value = "topic1", type = "topic"), //指定要绑定的交换机和类型
                    key = {"user.save", "user.*"}
            )
    })
    public void receive(String message) {
        System.out.println("TopicConsume receive1 message==:" + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//创建临时队列
                    exchange = @Exchange(value = "topic1", type = "topic"), //指定要绑定的交换机和类型
                    key = {"user.#"}  //
            )
    })
    public void receive2(String message) {
        System.out.println("TopicConsume receive2 message==:" + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//创建临时队列
                    exchange = @Exchange(value = "topic1", type = "topic"), //指定要绑定的交换机和类型
                    key = {"order.#"}  //
            )
    })
    public void receive3(String message) {
        System.out.println("TopicConsume receive3 message==:" + message);
    }
}
