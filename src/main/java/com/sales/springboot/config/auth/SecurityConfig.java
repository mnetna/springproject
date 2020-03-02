package com.sales.springboot.config.auth;

import com.sales.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()
                .and().antMatcher("/**")
                .authorizeRequests()
                    .antMatchers("/auth**", "/register**", "/css/**", "/images/**", "/js/**", "/scss/**", "/vendor/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()).anyRequest().authenticated()
                .and().oauth2Login()
                    .userInfoEndpoint().userService(customOAuth2UserService);
                //.and().logout().logoutSuccessUrl("/login");

    }
}
