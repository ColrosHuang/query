package com.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by Kevin on 2018/11/8.
 */
@RestController
public class KevinController {
    @RequestMapping("/kevin")
    Object kevin() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("1", "kevin");
        map.put("2", "is");
        map.put("3", "good");
        return map;
    }
}
