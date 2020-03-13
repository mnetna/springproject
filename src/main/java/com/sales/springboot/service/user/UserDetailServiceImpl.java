package com.sales.springboot.service.user;

/**
 * Created by lees on 2020-03-13
 */

import com.sales.springboot.domain.user.User;
import com.sales.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service(value = "userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));

        logger.info("[UserDetailServiceImpl] User Email : {" + user.getEmail() + "}");

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority());
    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
