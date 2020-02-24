package com.sales.springboot.web;

import com.sales.springboot.domain.account.Account;
import com.sales.springboot.domain.account.AccountRepository;
import com.sales.springboot.domain.posts.Posts;
import com.sales.springboot.domain.posts.PostsRepository;
import com.sales.springboot.web.dto.account.AccountSaveRequestDto;
import com.sales.springboot.web.dto.posts.PostsSaveRequestDto;
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
public class AccountAPIControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @After
    public void tearDown() throws Exception {
        accountRepository.deleteAll();
    }

    @Test
    public void insertAccount() throws Exception {
        String email = "sssolee90@gmail.com";
        String firstName = "lee";
        String lastName = "sol";
        String password = "dlthfdlthf";

        AccountSaveRequestDto requestDto = AccountSaveRequestDto.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .build();

        String url = "http://localhost:"+port+"/api/v1/account";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Account> all = accountRepository.findAll();

        Assertions.assertThat(all.get(0).getEmail()).isEqualTo(email);
    }
}
