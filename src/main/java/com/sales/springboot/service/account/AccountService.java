package com.sales.springboot.service.account;

import com.sales.springboot.domain.account.Account;
import com.sales.springboot.domain.account.AccountRepository;
import com.sales.springboot.web.dto.account.AccountResponseDto;
import com.sales.springboot.web.dto.account.AccountSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public String save(AccountSaveRequestDto accountSaveRequestDto) {
        return accountRepository.save(accountSaveRequestDto.toEntity()).getEmail();
    }

//    @Transactional
//    public AccountResponseDto findByUserid(String email) {
//        Account entity = accountRepository.findByUserid(email).orElseThrow(() -> new IllegalArgumentException("계정이 존재하지 않습니다."));
//        return new AccountResponseDto(entity);
//    }
}
