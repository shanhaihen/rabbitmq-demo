package com.shanhaihen.rabbitdemo.provider;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式
 * 不支持通配符
 */
public class DirectProvider {

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
         * 参数2：交换机类型 direct 路由模式
         */
        channel.exchangeDeclare("logs_direct", "direct");
        //发送消息
        String routingkey = "info";
        channel.basicPublish("logs_direct",routingkey,null,("这个是direct模型发布的基于route key:"+routingkey+":发送的消息").getBytes());

        channel.basicPublish("logs_direct","error",null,("这个是direct模型发布的基于route key:"+routingkey+":发送的消息").getBytes());



        channel.close();
        connection.close();
    }
}
