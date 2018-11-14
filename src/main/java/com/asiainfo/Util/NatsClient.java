package com.asiainfo.Util;

import io.nats.client.*;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class NatsClient {
    //连接串共享
    private static String userName;
    private static String passWord;
    private static Connection connection;

    public NatsClient() {
        if(connection == null) {
            connection = connect();
        }
    }

    //订阅者
    private Subscription subscribe;

    private Connection connect() {
        //初始化Nats客户端链接，通过链接来获取数据
        String[] serverUrls = {
                "nats://132.121.116.33:4102",
                "nats://132.121.116.32:4102",
                "nats://132.121.116.31:4102",
                "nats://132.121.116.33:4101",
                "nats://132.121.116.32:4101",
                "nats://132.121.116.31:4101" };
        userName = "data";
        passWord = "data";
        Options options = new Options.Builder().servers(serverUrls)
                .userInfo(userName, passWord).maxReconnects(-1).build();
        try {
            connection = Nats.connect(options);
            if(connection.getStatus() != Connection.Status.CONNECTED) {
                connection = Nats.connect(options);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Connect to Nats Server failed. please check nat cluster status?");
        }

        return connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public Subscription send(String subject, String replySubject, String sendData) {
        if (connection == null) {
            connection = connect();
        }

        //订阅该接收主题
        subscribe = connection.subscribe(replySubject);

        //发送数据
        connection.publish(subject, sendData.getBytes());

        //返回订阅者
        return subscribe;
    }

    public String recv() {
        if(connection == null) {
            connection = connect();
        }

        if(subscribe == null) {
            return null;
        }

        //从该主题接收一条数据
        try {
            Message message = subscribe.nextMessage(Duration.ofMillis(5000));
            return new String(message.getData(), StandardCharsets.UTF_8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String recv(Subscription subscribe) {
        if(connection == null) {
            connection = connect();
        }

        if (subscribe == null) {
            return null;
        }

        //从该主题接收一条数据
        try {
            Message message = subscribe.nextMessage(Duration.ofMillis(5000));
            return new String(message.getData(), StandardCharsets.UTF_8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
