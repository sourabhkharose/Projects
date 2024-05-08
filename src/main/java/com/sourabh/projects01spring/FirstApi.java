package com.sourabh.projects01spring;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstApi {

    @RequestMapping("/hello")
    public String sayHello() {
        return "Hello!";
    }

    @RequestMapping("/hello/{name}")
    public String helloName(@PathVariable("name") String name) {
        return "Hello " + name + "!";
    }

    @RequestMapping("/hello/{name}/{times}")
    public String repeatHello(@PathVariable("name") String name,
                              @PathVariable("times") int times) {
        String begin = "";
        for (int i = 0; i < times; i++) {
            begin += "Hello " + name + "! - " + (i+1);
            begin += "<br>";
        }
        return begin;
    }
}
