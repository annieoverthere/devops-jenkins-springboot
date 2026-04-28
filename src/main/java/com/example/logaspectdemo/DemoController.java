package com.example.logaspectdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from REST!";
    }

    @GetMapping("/item/{id}")
    public String getItem(@PathVariable String id) {
        return "Item ID: " + id;
    }
}
