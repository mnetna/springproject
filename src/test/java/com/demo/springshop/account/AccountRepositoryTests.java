package com.demo.springshop.account;

import com.demo.springshop.dao.AccountDao;
import com.demo.springshop.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest // 슬라이싱 테스트 & SpringBootTest로 하면 메인의 모든 Component가 bean으로 등록됨
public class AccountRepositoryTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountDao accountDao;

    @Test
    public void di() throws SQLException {
        Account account = new Account();
        account.setUserid("mnetna");
        account.setUsername("leesol");
        account.setPassword("password");

        Optional<Account> existingAccount = accountDao.findByUserid("mnetna");
        assertThat(existingAccount).isNotEmpty();

//        Optional<Account> notExistingAccount = accountDao.findByUsername("whiteship");
//        assertThat(notExistingAccount).isEmpty();
    }
}
