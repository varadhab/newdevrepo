package com.fintech.ordersource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloFintech {
	/*@GetMapping("/")
    public String index() {
        return "Welcome to Fintech Order Source Service";
    }*/

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
