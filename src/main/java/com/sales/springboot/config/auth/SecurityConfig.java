package com.sales.springboot.config.auth;

import com.sales.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 기능 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()
                .and().authorizeRequests()                                                                      // URL 권한 관리 시작
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() // 권한 관리 대상 지정, 모든 URL에 대해 열람 권한 지정
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()).anyRequest().authenticated()  // "/api/vi/**" URL에 대해서 USER 권한이 있는 대상만 접근 가능
                .and().logout().logoutSuccessUrl("/")                                                           // 로그아웃 진입점
                .and().oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);                   //OAuth2 로그인 성공 이후 후속 조치 수행
    }
}
