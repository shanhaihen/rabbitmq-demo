package com.shanhaihen.rabbitdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = RabbitDemoApplication.class)
@RunWith(SpringRunner.class)
public class SpringBootRabbitMQTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * route 路由模式
     */
    @Test
    public void routeTest() {
        rabbitTemplate.convertAndSend("route1", "info", "route1 模型发送的[info]消息");
        rabbitTemplate.convertAndSend("route1", "error", "route1 模型发送的[error]消息");
    }


    /**
     * fanout 广播
     *
     * @throws InterruptedException
     */
    @Test
    public void fanOutTest() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("fanout1", "", i + "fanout 模型发送的消息");
        }
    }

    /**
     * 生产者不创建队列
     */
    @Test
    public void simpleQueueTest() {
        rabbitTemplate.convertAndSend("hello", "hello world");
    }

    /**
     * 生产者不创建队列
     */
    @Test
    public void workQueueTest() {
        for (int i = 0; i < 10; i++) {
            System.out.println("=================>>>::" + i);
            rabbitTemplate.convertAndSend("spring_work1", i + "hello world");
        }
    }

}
