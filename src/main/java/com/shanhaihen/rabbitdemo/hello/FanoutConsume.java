package com.shanhaihen.rabbitdemo.hello;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 广播模式会把消息发给订阅的队列，每队列可以分别处理这个消息
 */
@Component
public class FanoutConsume {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//创建临时队列
                    exchange = @Exchange(value = "fanout1", type = "fanout") //指定要绑定的交换机
            )
    })
    public void receive(String message) {
        System.out.println("FanoutConsume receive1 message==:" + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//创建临时队列
                    exchange = @Exchange(value = "fanout1", type = "fanout") //指定要绑定的交换机
            )
    })
    public void receive2(String message) {
        System.out.println("FanoutConsume receive2 message==:" + message);
    }

}
