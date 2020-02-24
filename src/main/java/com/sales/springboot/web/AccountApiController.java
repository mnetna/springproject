package com.sales.springboot.web;

import com.sales.springboot.service.account.AccountService;
import com.sales.springboot.web.dto.account.AccountSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountApiController {
    private final AccountService accountService;

    @PostMapping("/api/v1/account")
    public String save(@RequestBody AccountSaveRequestDto accountSaveRequestDto) {
        return accountService.save(accountSaveRequestDto);
    }
}
