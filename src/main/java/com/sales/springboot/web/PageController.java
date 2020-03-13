package com.sales.springboot.web;

import com.sales.springboot.domain.user.User;
import com.sales.springboot.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@RequiredArgsConstructor
@Controller
public class PageController {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private UserService userService;

    @GetMapping("/")
    public String index() {
        logger.info("index Page 실행!");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        logger.info("login Page 실행!");
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        logger.info("register Page 실행!");
        return "register";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("signup") User user) {
        logger.info("signup Page 실행!");
        String token = userService.signUp(user);
        return UriComponentsBuilder.fromUriString("http://localhost:8081")
                .queryParam("auth_token", token)
                .build().toUriString();
    }
}
