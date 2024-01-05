package org.example.rabbitMQ.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // TODO: 1.创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // TODO: 2.设置RabbitMQ的地址
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        // TODO: 3.建立连接
        Connection connection = factory.newConnection();
        // TODO: 4.获得信道
        Channel channel = connection.createChannel();
        // TODO: 5.声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // TODO: 6.接收消息并消费
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("收到了消息：" + message);
            }
        });
    }
}
