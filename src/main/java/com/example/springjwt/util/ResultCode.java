package com.example.springjwt.util;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS("200", "success"),
    LOGIN_FAIL("900", "login fail"),
    INVALID_TOKEN("901", "invalid token"),
    EXPIRED_TOKEN("902", "expired token"),
    SERVER_ERROR("903", "server error");

    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
