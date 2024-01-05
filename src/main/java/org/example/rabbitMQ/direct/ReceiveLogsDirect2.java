package org.example.rabbitMQ.direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/*
    描述      接收EmitLogDirect生产的1种等级的log
 */
public class ReceiveLogsDirect2 {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // 生成一个随机的临时的queue
        String queueName = channel.queueDeclare().getQueue();
        // 一个交换机绑定1个queue
        channel.queueBind(queueName, EXCHANGE_NAME, "error");

        System.out.println("开始接收消息！");
        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("收到消息：" + message);
            }
        };
        channel.basicConsume(queueName, true, consumer);

    }
}
