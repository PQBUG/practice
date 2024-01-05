package org.example.rabbitMQ.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
    描述：         任务有所耗时，多个任务
 */
public class NewTask {
    private final static String TASK_QUEUE_NAME = "task_queue";

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
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        // TODO: 6.发布消息
        for (int i = 0; i < 10; i++) {
            String message;
            message = i + "...";
            channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println("发送了消息：" + message);
        }
        // TODO: 7.关闭连接
        channel.close();
        connection.close();
    }
}
