package com.shanhaihen.rabbitdemo.provider;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 广播模式 提供者
 */
public class FanoutProvider {
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
         * 将通道声明为指定的交换机
         * 参数1：表示交换机的名称
         * 参数2：交换机类型 fanout 广播类型
         */
        channel.exchangeDeclare("logs","fanout");


        //发布消息
        /**
         * 简单队列 没有交换机
         * 参数1 交换机
         * 参数2：队列名称 对于广播来说此参数没有意义
         * 参数3： 传递消息额外设置
         * 参数4: 具体的消息内容
         */
        for (int i=0;i<10;i++) {
            channel.basicPublish("logs", "", null, (i+"：fanout type message").getBytes());
        }

        channel.close();
        connection.close();
    }
}
