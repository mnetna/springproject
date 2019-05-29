package com.demo.springshop.service.impl;

import com.demo.springshop.service.LoginService;

public class LoginServiceImpl implements LoginService {

    @Override
    public boolean passwordValidation(String pwd01, String pwd02) {
        return false;
    }
}
