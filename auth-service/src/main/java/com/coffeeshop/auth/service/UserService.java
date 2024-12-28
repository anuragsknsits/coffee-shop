package com.coffeeshop.auth.service;

import com.coffeeshop.auth.model.AuthenticationRequest;
import com.coffeeshop.auth.model.SignUp;

public interface UserService {
    void registerUser(SignUp signUp);

    String authenticateUser(AuthenticationRequest authenticationRequest);

    String getUserName(String token);

    void updateUser(Long userId, SignUp signUp);

    void deleteUser(Long userId);

}
