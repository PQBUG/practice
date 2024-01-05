package org.example.rabbitMQ.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ReceiveLogTopic1 {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        // 生成一个随机的临时的queue
        String queueName = channel.queueDeclare().getQueue();
        String routingKey = "*.orange.*";
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);


        System.out.println("开始接收消息！");
        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("收到消息：" + message + " routingKey is " + envelope.getRoutingKey());
            }
        };
        channel.basicConsume(queueName, true, consumer);

    }
}
