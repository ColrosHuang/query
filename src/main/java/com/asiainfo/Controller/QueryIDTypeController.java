package com.asiainfo.Controller;

import com.asiainfo.Util.NatsClient;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.Subscription;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
public class QueryIDTypeController {

    @RequestMapping(value = "/queryId/{queryId}")
    public String queryIDType(
            @PathVariable("queryId") String queryId
    ) {
        //System.out.println(queryId);
        try {
            NatsClient client = new NatsClient();
            Subscription subscription1 = client.getConnection().subscribe("hello");
            Subscription subscription2 = client.getConnection().subscribe("hello1");
            Subscription subscription3 = client.getConnection().subscribe("hello2");

            client.send("QueryDaemonForJava","hello", queryId);
            String msg1 = new String(subscription1.nextMessage(Duration.ofMillis(5000)).getData());
            String msg2 = new String(subscription2.nextMessage(Duration.ofMillis(5000)).getData());
            String msg3 = new String(subscription3.nextMessage(Duration.ofMillis(5000)).getData());

            return "hi: " + msg1 + msg2 + msg3;
            subscription1.unsubscribe();


        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }


        return "hi no reply";
    }
}