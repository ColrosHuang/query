package com.Controller;

import com.asiainfo.POJO.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Kevin on 2018/11/8.
 */
@RestController
public class HelloController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/hello")
    public Greeting Greeting(@RequestParam(value = "name", defaultValue = "I'am Kevin Huang, Author of this web") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
