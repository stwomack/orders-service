package com.demo.ordersservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @GetMapping("/")
    public String hello() {
        return "This Spring Boot app is generating orders and storing them in MongoDB";
    }
}
