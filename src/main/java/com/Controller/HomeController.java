package com.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by Kevin on 2018/11/8.
 */
@RestController
@RequestMapping({"/", "/home"})
public class HomeController {
/*	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String hello() {
		return "hello world";
	}*/
	@RequestMapping(method = RequestMethod.GET)
	public Object hello() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("key1", "hello");
		map.put("key2", "world");
		return map;
	}
}
