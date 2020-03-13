package com.sales.springboot.config.auth.dto;

/**
 * Created by lees on 2020-03-13
 */

public enum SocialType {

    FACEBOOK("facebook"),
    GOOGLE("google"),
    KAKAO("kakao"),
    NAVER("naver");

    private String name;

    SocialType(String name) { this.name = name; }

    public String getValue() { return name; }

}
