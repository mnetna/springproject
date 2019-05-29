package com.demo.springshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String index() {
        return "login_register_modal";
    }

    @GetMapping("/submit")
    public String submit() {
        return "";
    }
}
