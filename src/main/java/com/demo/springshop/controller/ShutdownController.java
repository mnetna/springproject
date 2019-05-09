package com.demo.springshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShutdownController {

    @GetMapping("/shutdown")
    public String shutdown() throws InterruptedException {
        Thread.sleep(10000);
        return "Process finished";
    }
}
