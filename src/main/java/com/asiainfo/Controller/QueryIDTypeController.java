package com.asiainfo.Controller;

import com.asiainfo.Util.NatsClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryIDTypeController {

    @RequestMapping(value = "/queryId/{queryId}")
    public String queryIDType(
            @PathVariable("queryId") String queryId
    ) {
        //System.out.println(queryId);
//		NatsClient client = new NatsClient();

        //发送数据并接收一条返回
        //String msg = client.request("QueryDaemonForJava", queryId, queryId, Duration.ofSeconds(5));
        String msg = NatsClient.nats_request("QueryDaemonForJava", queryId, queryId);
        if (msg == null) {
            return "hi no reply";
        } else
            return "hi: " + msg;

    }
}