package com.shanhaihen.rabbitdemo.consume;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式
 * 不支持通配符
 */
public class TopicConsume {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    createFanoutConsume();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private static void createFanoutConsume() throws IOException, TimeoutException {
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
         * 通道绑定交换机
         */
        channel.exchangeDeclare("logs_topic", "topic");
        /**
         * 創建临时队列
         */
        String queueName = channel.queueDeclare().getQueue();

        /**
         * 绑定交换机和队列
         * 动态通配符的方式
         * *代表一个单词
         * #代表多个
         */
//        channel.queueBind(queueName, "logs_topic", "user.*");
        channel.queueBind(queueName, "logs_topic", "user.#");


        /**
         * 消费消息
         * 参数1：消费哪个队列的消息
         * 参数2： 开启消息的自动确认机制
         * 参数2：消费消息时的回调接口
         */
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
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
                System.out.println("topicConsume================>body：" + new String(body));
            }
        });
        //
//        Thread.sleep(10000l);
//        channel.close();
//        connection.close();
    }
}
