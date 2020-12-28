package com.shanhaihen.rabbitdemo.hello;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RouteConsume {
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//创建临时队列
                    exchange = @Exchange(value = "route1", type = "direct"), //指定要绑定的交换机和类型
                    key = {"info", "error"}
            )
    })
    public void receive(String message) {
        System.out.println("RouteConsume receive1 message==:" + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//创建临时队列
                    exchange = @Exchange(value = "route1", type = "direct"), //指定要绑定的交换机和类型
                    key = {"error"}  //只接收error
            )
    })
    public void receive2(String message) {
        System.out.println("RouteConsume receive2 message==:" + message);
    }
}
