package com.shanhaihen.rabbitdemo.consume;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列
 * 解决丢失问题 ，不自动确认，每次只消费一条消息
 * 如果存在未确认的数据，会等消费者下线后，重新分配对应的消费者
 */
public class WorkQueueConsume1 {
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
         * 每次只消费一条消息
         */
        channel.basicQos(1);

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
        channel.basicConsume("workQueue", false, new DefaultConsumer(channel) {
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
                /**
                 * 参数1： 确认的是队列种哪个具体的消息
                 * 参数2： 是否开启多个消息同时确认
                 */
                if (name.equals("body1"))
                    channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
