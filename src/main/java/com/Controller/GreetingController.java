package com.Controller;

import com.asiainfo.POJO.Greeting;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * Created by Kevin on 2018/11/8.
 */

class GreetingException extends RuntimeException {
    private long errId;
    public GreetingException(long errId) {
        this.errId = errId;
    }
    public long getErrId() {
        return errId;
    }
};


@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @RequestMapping(value = "/{name}")
    public Greeting sayGreeting(
            @PathVariable String name
    ) {
        System.out.println(name);
        name = null;
        Greeting greeting = new Greeting(999, "hello");

        if(name == null)
            throw new GreetingException(12);
        else
            return greeting;
    }

    @ExceptionHandler(GreetingException.class)
    @ResponseStatus(HttpStatus.CREATED)
    public Greeting notFound(GreetingException e) {
        long id = e.getErrId();
        Greeting greeting = new Greeting(id, "你好");
        return greeting;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Greeting> sayHi(@RequestBody Greeting greeting) {
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = URI.create("hello/id/123");
        headers.setLocation(locationUri);


        ResponseEntity<Greeting> responseEntity = new ResponseEntity<Greeting>(greeting, headers, HttpStatus.CREATED);

        return responseEntity;
    }

}
