package com.sales.springboot.config.auth;

import com.sales.springboot.config.auth.jwt.JwtTokenUtil;
import com.sales.springboot.domain.user.User;
import com.sales.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Created by lees on 2020-03-13
 */

@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private String homeUrl = "http://localhost:8081";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("Success Handler 진입 성공!");

        if (response.isCommitted()) {
            return;
        }

        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        Map attributes = oidcUser.getAttributes();
        String email = (String) attributes.get("email");

        Optional<User> user = userRepository.findByEmail(email);
        getRedirectStrategy().sendRedirect(request, response, user.isPresent() ? getRedirectURL(user.get()) : homeUrl + "/register");
    }

    private String getRedirectURL(User user) {
        String token = jwtTokenUtil.generateToken(user);
        String redirectionUrl = UriComponentsBuilder.fromUriString(homeUrl)
                .queryParam("auth_token", token)
                .build().toUriString();
        return redirectionUrl;
    }
}
