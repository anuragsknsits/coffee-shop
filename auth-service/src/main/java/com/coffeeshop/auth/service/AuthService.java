package com.coffeeshop.auth.service;


import com.coffeeshop.auth.model.SignUp;

public interface AuthService {
    String signUp(SignUp signUp);

    String login(String userName, String password);
}
