package com.Controller;

import com.asiainfo.Util.NatsClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by Kevin on 2018/11/8.
 */
@RestController
public class HomeController {
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public Object hello() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key1", "hello");
        map.put("key2", "world");
        map.put("key3", "world");
        NatsClient.nats_request("QueryDaemonForJava", map.get("key1"), map.get("key1"));
        return map;
    }
}
