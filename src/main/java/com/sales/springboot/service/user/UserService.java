package com.sales.springboot.service.user;

import com.sales.springboot.config.auth.jwt.JwtTokenUtil;
import com.sales.springboot.domain.user.User;
import com.sales.springboot.domain.user.UserRepository;
import com.sales.springboot.web.dto.account.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lees on 2020-03-13
 */

@RequiredArgsConstructor
@Service
public class UserService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptEncoder;

    @Transactional
    public String signUp(User user) {
        User dbUser = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException("User already exist."));
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return jwtTokenUtil.generateToken(user);
    }

    @Transactional
    public String save(UserSaveRequestDto userSaveRequestDto) {
        return userRepository.save(userSaveRequestDto.toEntity()).getEmail();
    }
}
