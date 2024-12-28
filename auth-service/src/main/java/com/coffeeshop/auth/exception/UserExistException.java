package com.coffeeshop.auth.exception;

public class UserExistException extends RuntimeException {
    public UserExistException(String userName) {
        super(userName);
    }
}
