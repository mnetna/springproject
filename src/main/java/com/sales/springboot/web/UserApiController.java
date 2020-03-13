package com.sales.springboot.web;

import com.sales.springboot.service.user.UserService;
import com.sales.springboot.web.dto.account.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @GetMapping("/api/v1/account")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        System.out.println(oAuth2User.getAttributes());
        return Collections.singletonMap("email", oAuth2User.getAttributes().get("email"));
    }

    @PostMapping("/api/v1/account")
    public String save(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        return userService.save(userSaveRequestDto);
    }


}
