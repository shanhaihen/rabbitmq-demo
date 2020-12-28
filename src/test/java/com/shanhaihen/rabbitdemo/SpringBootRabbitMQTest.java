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
     * topic 动态路由 订阅模式
     */
    @Test
    public void topicTest() {
        rabbitTemplate.convertAndSend("topic1", "user", "topic模型发送的[user]消息");
        rabbitTemplate.convertAndSend("topic1", "user.save", "topic模型发送的[user.save]消息");
        rabbitTemplate.convertAndSend("topic1", "user.save.query", "topic 模型发送的[user.save.query]消息");
        rabbitTemplate.convertAndSend("topic1", "order", "topic模型发送的[order]消息");
    }


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
