package com.sales.springboot.web.dto.account;

import com.sales.springboot.domain.account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AccountSaveRequestDto {

    private String email;
    private String firstName;
    private String lastName;
    private String password;

    @Builder
    public AccountSaveRequestDto(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public Account toEntity() {
        return Account.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .build();
    }
}
