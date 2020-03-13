package com.sales.springboot.web;

import com.sales.springboot.domain.user.User;
import com.sales.springboot.domain.user.UserRepository;
import com.sales.springboot.web.dto.account.UserSaveRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAPIControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void insertAccount() throws Exception {
        String email = "sssolee90@gmail.com";
        String name = "lee sol";
        String password = "dlthfdlthf";
        String picture = "111";

        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .name(name)
                .email(email)
                .password(password)
                .picture(picture)
                .build();

        String url = "http://localhost:"+port+"/api/v1/account";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<User> all = userRepository.findAll();

        Assertions.assertThat(all.get(0).getEmail()).isEqualTo(email);
    }
}
