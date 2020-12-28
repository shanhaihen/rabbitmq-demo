package com.shanhaihen.rabbitdemo.provider;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列
 */
public class WorkQueueProvider {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建链接mq的工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("rabbitmq.shanhaihen.com");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ems");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");
        Connection connection = connectionFactory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        /**
         * 通道绑定对应的消息队列
         * 参数1： 队列名称，会自动创建
         * 参数2： 用来定义队列的特性，是否需要持久化 true:持久化 false:不持久化
         * 参数3：是否独占队列 true:独占 false 不独占
         * 参数4： 是否在消费完成后自动删除队列 true：自动删除 false:不自动删除
         * 参数5：附加参数
         */
        channel.queueDeclare("workQueue", false, false, false, null);

        //发布消息
        /**
         * 简单队列 没有交换机
         * 参数1 交换机
         * 参数2：队列名称
         * 参数3： 传递消息额外设置
         * 参数4: 具体的消息内容
         */
        for (int i=0;i<10;i++) {
            channel.basicPublish("", "workQueue", null, (i+"：===hello rebbitmq").getBytes());
        }

        channel.close();
        connection.close();
    }
}
