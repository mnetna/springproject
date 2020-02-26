package com.sales.springboot.config.auth.dto;

import com.sales.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/*
* Session에 User 클래스 자체를 저장하면 직렬화를 구현하지 않아 에러 발생
* 만약 User 클래스에 직렬화를 구현한다 하더라도 User 클래스는 Entity 클래스이기 때문에
* 다른 엔티티와 관계과 형성될 수 있음에 따라 자식 엔티티까지 직렬화 대상에 포함되어 성능 이슈, 부수 효과가 발생함
* 따라서 직렬화 기능을 가진 세션 dto를 추가로 생성하는 것이 맞음
* */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
