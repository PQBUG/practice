package org.example.rabbitMQ.workqueues;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
    描述：接收NewTask的消息
 */
public class Worker {
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
        // TODO: 6.接收消息
        System.out.println("开始接收消息！");
        channel.basicConsume(TASK_QUEUE_NAME, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("收到了消息："+message);
                try {
                    doWork(message);
                }finally {
                    System.out.println("消息处理完成！");
                }

            }
        });

    }

    private static void doWork(String task){
        char[] chars = task.toCharArray();
        for (char ch : chars) {
            if (ch=='.'){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
