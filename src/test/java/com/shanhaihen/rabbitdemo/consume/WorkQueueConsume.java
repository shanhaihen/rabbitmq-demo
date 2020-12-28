package com.shanhaihen.rabbitdemo.consume;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列
 * 默认 消费者会平均分配，循环，
 * 一次性把数据全部给消费者，自动确认成功，消费者如果报错数据就有可能丢失
 * 会堆积消息
 */
public class WorkQueueConsume {
    public static void main(String[] args) throws IOException, TimeoutException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    consumeMethod("body1");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    consumeMethod("body22222");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private static void consumeMethod(String name) throws IOException, TimeoutException {
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

        /**
         * 消费消息
         * 参数1：消费哪个队列的消息
         * 参数2： 开启消息的自动确认机制
         * 参数2：消费消息时的回调接口
         */
        channel.basicConsume("workQueue", true, new DefaultConsumer(channel) {
            /**
             * 处理消息回调
             * @param consumerTag 标签
             * @param envelope
             * @param properties 属性
             * @param body 参数
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //如果通道关闭的话，则这个回调可能没有调用到，所以不建议关闭通道
                System.out.println(name + "================>body：" + new String(body));
            }
        });
    }
}
