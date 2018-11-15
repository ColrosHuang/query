package com.asiainfo.Util;

import io.nats.client.*;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class NatsClient {
    //连接串共享
    private static String userName;
    private static String passWord;
    private static Connection connection;

    public NatsClient() {
        if (connection == null) {
            connection = connect();
        }
    }

    public static String nats_request(String subject, String replySubject, String sendData) {
        NatsClient client = new NatsClient();
        return client.request(subject, replySubject, sendData);
    }

    public static String nats_request(String subject, String replySubject, String sendData, Duration duration) {
        NatsClient client = new NatsClient();
        return client.request(subject, replySubject, sendData, duration);
    }

    private Connection connect() {
        //初始化Nats客户端链接，通过链接来获取数据
        String[] serverUrls = {
                "nats://132.121.116.33:4102",
                "nats://132.121.116.32:4102",
                "nats://132.121.116.31:4102",
                "nats://132.121.116.33:4101",
                "nats://132.121.116.32:4101",
                "nats://132.121.116.31:4101"};
        userName = "data";
        passWord = "data";
        Options options = new Options.Builder().servers(serverUrls)
                .userInfo(userName, passWord).maxReconnects(-1).build();
        try {
            connection = Nats.connect(options);
            if (connection.getStatus() != Connection.Status.CONNECTED) {
                connection = Nats.connect(options);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connect to Nats Server failed. please check nat cluster status?");
        }

        return connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public String request(String subject, String replySubject, String sendData) {
        //默认不带超时时间的话，超时时间为1秒
        return request(subject, replySubject, sendData, Duration.ofMillis(1000));
    }

    public String request(String subject, String replySubject, String sendData, Duration duration) {
        //获取一个Nats连接
        if (connection == null) {
            connection = connect();
        }

        //从连接中获取一个订阅者接收指定主题
        Subscription subscriber = connection.subscribe(replySubject);
        if (subscriber == null) {
            return null;
        }

        //开始发送数据到发送主题上
        connection.publish(subject, sendData.getBytes());

        //从指定主题的订阅者中接收一条数据
        try {
            Message message = subscriber.nextMessage(duration);
            if (message != null) {
                return new String(message.getData(), StandardCharsets.UTF_8);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
