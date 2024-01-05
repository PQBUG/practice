package org.example.rabbitMQ.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
    描述：         Hello World的发送类，连接到RabbitMQ的服务器端，然后发送一条消息，退出
 */
public class Send {
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
        // TODO: 6.发布消息
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println("发送了消息：" + message);
        // TODO: 7.关闭连接
        channel.close();
        connection.close();
    }
}
