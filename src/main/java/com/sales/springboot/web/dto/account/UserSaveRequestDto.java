package com.sales.springboot.web.dto.account;

import com.sales.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
public class UserSaveRequestDto {

    private String name;
    private String email;
    private String picture;
    private String password;

    @Builder
    public UserSaveRequestDto(String name, String email, String picture, String password) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .picture(picture)
                .build();
    }
}
