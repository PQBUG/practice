package org.example.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/*
    描述      Direct类型交换机
 */
public class EmitLogDirect {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String message = "info: Hello World!";
        channel.basicPublish(EXCHANGE_NAME, "info", null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("发送了消息, 等级为info: " + message);

        message = "warning: Hello World!";
        channel.basicPublish(EXCHANGE_NAME, "warning", null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("发送了消息, 等级为warning: " + message);

        message = "error: Hello World!";
        channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("发送了消息, 等级为error: " + message);

        channel.close();
        connection.close();

    }
}
